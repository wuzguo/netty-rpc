package com.sunvalley.rpc.consumer.proxy;

import com.sunvalley.rpc.consumer.pool.NettyPool;
import java.lang.reflect.Proxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 15:00
 */

public class ReferenceFactoryBean implements FactoryBean<Object> {

    private Class<?> interfaceClass;

    private String version;

    private long timeout;

    private Object object;

    @Autowired
    private NettyPool nettyPool;

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    /**
     * 初始化方法，Bean被构造的时候会调用
     *
     * @throws Exception
     */
    public void init() throws Exception {
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
            new InvokerProxy(version, nettyPool, timeout));
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
