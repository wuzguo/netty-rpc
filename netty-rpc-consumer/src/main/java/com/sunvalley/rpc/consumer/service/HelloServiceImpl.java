package com.sunvalley.rpc.consumer.service;

import com.sunvalley.rpc.consumer.pool.NettyPool;
import com.sunvalley.rpc.core.domain.RpcRequest;
import com.sunvalley.rpc.core.domain.RpcResponse;
import com.sunvalley.rpc.facade.service.IHelloService;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 17:57
 */

@Slf4j
@Service
public class HelloServiceImpl implements IHelloService {

    private static final NettyPool NETTY_POOL = NettyPool.getPool();

    @Override
    public String greet(String name) {
        RpcRequest request = RpcRequest.builder().requestId(RequestHolder.idGen())
            .className(IHelloService.class.getName()).methodName("greet").params(new Object[]{name})
            .paramTypes(new Class[]{String.class}).build();
        NETTY_POOL.sendMessage(request);

        try {
            return Optional.ofNullable(RequestHolder.remove(request.getRequestId()).get()).map(RpcResponse::getValue)
                .map(String::valueOf).orElse(null);
        } catch (InterruptedException | ExecutionException e) {
            log.error("hello service greet occurred exception: {}", e.getMessage());
        }

        return null;
    }
}
