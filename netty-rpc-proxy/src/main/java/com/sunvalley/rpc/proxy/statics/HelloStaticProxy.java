package com.sunvalley.rpc.proxy.statics;

import com.sunvalley.rpc.proxy.service.IHelloService;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 10:44
 */

public class HelloStaticProxy {

    private final IHelloService target;


    public HelloStaticProxy(IHelloService target) {
        this.target = target;
    }

    /**
     * 代理方法
     *
     * @param name 名称
     */
    public void greet(String name) {
        // 扩展功能
        System.out.println("静态代码前置日志");
        target.greet(name);
        System.out.println("静态代码后置日志");
    }
}
