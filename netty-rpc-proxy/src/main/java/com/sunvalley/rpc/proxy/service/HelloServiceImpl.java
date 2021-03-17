package com.sunvalley.rpc.proxy.service;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 10:43
 */

public class HelloServiceImpl implements IHelloService {

    @Override
    public void greet(String name) {
        System.out.println("Hello , " + name);
    }
}
