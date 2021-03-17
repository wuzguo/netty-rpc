package com.sunvalley.rpc.server.provider;


import com.sunvalley.rpc.core.codec.PacketDecoder;
import com.sunvalley.rpc.core.codec.PacketEncoder;
import com.sunvalley.rpc.server.handler.ExceptionHandler;
import com.sunvalley.rpc.server.handler.ServerInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:46
 */

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("exceptionHandler", new ExceptionHandler());
        pipeline.addLast("serverInboundHandler", new ServerInboundHandler());
    }
}
