package com.sunvalley.rpc.facade.service;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 13:57
 */

public interface IHelloService {

    /**
     * 接口方法
     *
     * @param name 名称
     * @return {@link String}
     */
    String greet(String name);
}
