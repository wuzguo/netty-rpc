package com.sunvalley.rpc.consumer.pool;

import com.google.common.collect.Lists;
import com.sunvalley.rpc.consumer.handler.ClientInboundHandler;
import com.sunvalley.rpc.consumer.handler.ExceptionHandler;
import com.sunvalley.rpc.consumer.service.RequestHolder;
import com.sunvalley.rpc.core.codec.PacketDecoder;
import com.sunvalley.rpc.core.codec.PacketEncoder;
import com.sunvalley.rpc.core.domain.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.FutureListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 15:09
 */

@Slf4j
public class NettyPool {

    /**
     * key为目标host，value为目标host的连接池
     */
    private static ChannelPoolMap<String, FixedChannelPool> mapChannelPools;

    /**
     * 主机名称
     */
    private final String hostName;

    /**
     * 端口
     */
    private final Integer port;


    /**
     * 构造函数
     *
     * @param hostName 主机名称
     * @param port     端口
     */
    private NettyPool(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
        initialize();
    }

    private void initialize() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
            .remoteAddress(this.hostName, this.port);
        List<ChannelHandler> channelHandlers = Lists.newArrayList();
        // 各种Handler
        channelHandlers.add(new PacketDecoder());
        channelHandlers.add(new PacketEncoder());
        channelHandlers.add(new ExceptionHandler());
        channelHandlers.add(new ClientInboundHandler());
        create0(bootstrap, channelHandlers);
    }

    /**
     * netty连接池使用
     *
     * @param bootstrap {@link Bootstrap}
     */
    private void create0(@NonNull Bootstrap bootstrap, Collection<ChannelHandler> channelHandlers) {
        mapChannelPools = new AbstractChannelPoolMap<String, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(String key) {
                ChannelPoolHandler handler = new ChannelPoolHandler() {
                    /**
                     * 使用完channel需要释放才能放入连接池
                     *
                     */
                    @Override
                    public void channelReleased(Channel ch) throws Exception {
                        // 刷新管道里的数据
                        log.info("channel released ......");
                    }

                    /**
                     * 当链接创建的时候添加channelhandler，只有当channel不足时会创建，但不会超过限制的最大channel数
                     *
                     */
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        Optional.ofNullable(channelHandlers).orElse(Collections.emptyList()).forEach(pipeline::addLast);
                    }

                    /**
                     *  获取连接池中的channel
                     *
                     */
                    @Override
                    public void channelAcquired(Channel ch) throws Exception {
                        log.info("channel acquired ......");
                    }
                };

                // 单个host连接池大小
                return new FixedChannelPool(bootstrap, handler, 50);
            }
        };

    }

    /**
     * 发送请求
     *
     * @param key     Pool Key
     * @param message 消息
     */
    private void send(@NonNull String key, @NonNull RpcRequest message) {
        // 从连接池中获取连接
        FixedChannelPool pool = mapChannelPools.get(key);
        // 申请连接，没有申请到或者网络断开，返回null
        pool.acquire().addListener((FutureListener<Channel>) future -> {
            //给服务端发送数据
            Channel channel = future.getNow();
            channel.writeAndFlush(message);
            RequestHolder.put(message.getRequestId(), new DefaultPromise<>(new DefaultEventLoop()));
            // 连接放回连接池，这里一定记得放回去
            pool.release(channel);
        });
    }

    /**
     * 发送消息
     *
     * @param message {@link Object}
     */
    public void sendMessage(@NonNull RpcRequest message) {
        this.send(String.format("%s:%s", this.hostName, this.port), message);
    }


    /**
     * 获取连接池
     *
     * @return {@link NettyPool}
     */
    public static NettyPool getPool() {
        return new NettyPool("localhost", 6668);
    }
}
