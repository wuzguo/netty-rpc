package com.sunvalley.rpc.server.provider;

import com.sunvalley.rpc.core.annotation.RpcService;
import com.sunvalley.rpc.server.utils.ServiceUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:16
 */

@Slf4j
public class ServerProvider implements InitializingBean, BeanPostProcessor {

    /**
     * 服务地址
     */
    private String addr;

    /**
     * 端口号
     */
    private final Integer port;


    public ServerProvider(Integer port) {
        this.port = port;
    }

    private void startServer() throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        this.addr = InetAddress.getLocalHost().getHostAddress();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer()).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true).handler(new LoggingHandler(LogLevel.DEBUG));
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(this.addr, this.port)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(String.format("服务启动成功，地址 %s, 端口号 %s", this.addr, this.port));
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startServer();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional.ofNullable(bean.getClass().getAnnotation(RpcService.class))
            .ifPresent(rpcService -> ServiceUtils.put(rpcService.facade().getName(), rpcService.version(), bean));
        return bean;
    }
}
