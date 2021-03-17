package com.sunvalley.rpc.proxy.service;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 10:40
 */

public interface IHelloService {

    /**
     * 代理接口
     *
     * @param name 名称
     * @return {@link String}
     */
    void greet(String name);
}
