package com.sunvalley.rpc.consumer.proxy;

import com.sunvalley.rpc.consumer.pool.NettyPool;
import com.sunvalley.rpc.consumer.handler.RequestHolder;
import com.sunvalley.rpc.core.domain.RpcRequest;
import com.sunvalley.rpc.core.domain.RpcResponse;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 14:40
 */

@Slf4j
public class InvokerProxy implements InvocationHandler {

    /**
     * 版本号
     */
    private final String version;


    /**
     * 客户端
     */
    private final NettyPool nettyPool;


    /**
     * 超时时间
     */
    private final Long timeout;


    public InvokerProxy(String version, NettyPool nettyPool, Long timeout) {
        this.version = version;
        this.nettyPool = nettyPool;
        this.timeout = timeout;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = RpcRequest.builder().requestId(RequestHolder.idGen())
            .className(method.getDeclaringClass().getName()).methodName(method.getName()).params(args).version(version)
            .paramTypes(method.getParameterTypes()).build();

        try {
            Promise<RpcResponse> promise = new DefaultPromise<>(new DefaultEventLoop());
            RequestHolder.put(request.getRequestId(), promise);
            nettyPool.sendRequest(request);
            return Optional.ofNullable(promise.get()).map(RpcResponse::getValue).orElse(null);
        } catch (InterruptedException | ExecutionException e) {
            log.error("hello service greet occurred exception: {}", e.getMessage());
        }

        return null;
    }
}
