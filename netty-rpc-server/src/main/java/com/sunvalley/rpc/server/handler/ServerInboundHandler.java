package com.sunvalley.rpc.server.handler;

import com.sunvalley.rpc.core.domain.RpcRequest;
import com.sunvalley.rpc.core.domain.RpcResponse;
import com.sunvalley.rpc.server.utils.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Objects;
import org.springframework.util.ReflectionUtils;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class ServerInboundHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        System.out.println("server channel read message: " + request);
        ctx.channel().writeAndFlush(handle(request));
    }

    /**
     * 反射调用方法
     *
     * @param request 请求参数
     * @return {@link Object} 方法返回值
     * @throws Exception 异常
     */
    private RpcResponse handle(RpcRequest request) throws Exception {
        // 获取类名
        Object serviceBean = BeanUtils.get(request.getClassName(), request.getVersion());
        if (serviceBean == null) {
            throw new RuntimeException(String.format("service %s not exist", request.getClassName()));
        }

        // 反射调用
        Object value = ReflectionUtils.invokeMethod(Objects.requireNonNull(
                ReflectionUtils.findMethod(serviceBean.getClass(), request.getMethodName(), request.getParamTypes())),
            serviceBean, request.getParams());
        return RpcResponse.builder().value(value).requestId(request.getRequestId()).build();
    }
}
