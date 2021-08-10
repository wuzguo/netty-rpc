package com.sunvalley.rpc.server.provider;

import com.sunvalley.rpc.server.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:16
 */

@Slf4j
public class ServerProvider implements InitializingBean {

    /**
     * 服务地址
     */
    private final String hostname;

    /**
     * 端口号
     */
    private final Integer port;


    public ServerProvider(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    private void startServer() throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer()).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true).handler(new LoggingHandler(LogLevel.DEBUG));
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(this.hostname, this.port)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.printf("服务启动成功，地址 %s, 端口号 %s", this.hostname, this.port);
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
}
