package com.sunvalley.rpc.consumer.service;

import com.google.common.collect.Maps;
import com.sunvalley.rpc.core.domain.RpcResponse;
import io.netty.util.concurrent.Promise;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 9:44
 */

@UtilityClass
public class RequestHolder {

    private final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);


    private final static Map<Long, Promise<RpcResponse>> mapPromises = Maps.newConcurrentMap();

    /**
     * 获取请求ID
     *
     * @return {@link Long} id
     */
    public static Long idGen() {
        return REQUEST_ID_GEN.incrementAndGet();
    }

    /**
     * 添加
     *
     * @param reqId   请求ID
     * @param promise {@link Promise}
     */
    public static void put(Long reqId, Promise<RpcResponse> promise) {
        mapPromises.put(reqId, promise);
    }

    /**
     * 移除某个键
     *
     * @param reqId 请求ID
     * @return {@link Promise}
     */
    public static Promise<RpcResponse> remove(Long reqId) {
        return mapPromises.remove(reqId);
    }
}
