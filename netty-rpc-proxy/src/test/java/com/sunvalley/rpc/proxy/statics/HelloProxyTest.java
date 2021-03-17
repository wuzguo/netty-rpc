package com.sunvalley.rpc.proxy.statics;

import com.sunvalley.rpc.proxy.cglib.HelloCglibProxy;
import com.sunvalley.rpc.proxy.dynamic.HelloDynamicProxy;
import com.sunvalley.rpc.proxy.service.HelloServiceImpl;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloProxyTest {

    @Test
    public void testStatic() {
        HelloStaticProxy proxy = new HelloStaticProxy(new HelloServiceImpl());
        proxy.greet("Tomcat");
    }

    @Test
    public void testDynamic() {
        HelloDynamicProxy proxy = new HelloDynamicProxy(new HelloServiceImpl());
        proxy.greet("Tomcat");
    }

    @Test
    public void testCglib() {
        HelloCglibProxy proxy = new HelloCglibProxy(new HelloServiceImpl());
        proxy.greet("Tomcat");
    }
}