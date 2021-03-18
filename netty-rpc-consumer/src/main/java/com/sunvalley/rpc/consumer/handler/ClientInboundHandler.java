package com.sunvalley.rpc.consumer.handler;

import com.sunvalley.rpc.core.domain.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 18:12
 */

@Slf4j
public class ClientInboundHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        log.info("channel: {}, response: {}", ctx.channel(), response);
        long requestId = response.getRequestId();
        Promise<RpcResponse> future = RequestHolder.remove(requestId);
        future.setSuccess(response);
    }
}
