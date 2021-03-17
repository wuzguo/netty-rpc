package com.sunvalley.rpc.proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 11:04
 */

public class ProxyFactory {

    private Object target;


    public ProxyFactory(Object target) {
        this.target = target;
    }


    /**
     * newProxyInstance(ClassLoader loader,  // 指定当前目标对象使用类加载器
     * <p>
     * Class<?>[] interfaces,    // 目标对象实现的接口的类型
     * <p>
     * InvocationHandler h      // 事件处理器 )
     * <p>
     * //返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序。
     *
     * @return {@link Object} 对象
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
            (proxy, method, args) -> {
                System.out.println("动态代理前置日志");
                // 执行目标对象方法
                Object returnValue = method.invoke(target, args);
                System.out.println("动态代理后置日志");
                return returnValue;
            });
    }
}
