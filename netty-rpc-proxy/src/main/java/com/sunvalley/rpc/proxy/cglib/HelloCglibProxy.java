package com.sunvalley.rpc.proxy.cglib;

import com.sunvalley.rpc.proxy.service.IHelloService;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 11:26
 */

public class HelloCglibProxy {

    private final IHelloService target;

    public HelloCglibProxy(IHelloService target) {
        this.target = target;
    }

    public void greet(String name) {
        IHelloService helloDao = (IHelloService) new ProxyFactory(target).getProxyInstance();
        helloDao.greet(name);
    }
}
