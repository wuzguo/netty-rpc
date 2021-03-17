package com.sunvalley.rpc.server.service;

import com.sunvalley.rpc.core.annotation.RpcService;
import com.sunvalley.rpc.facade.service.IHelloService;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:11
 */

@RpcService(facade = IHelloService.class)
public class HelloServiceImpl implements IHelloService {

    @Override
    public String greet(String name) {
        return "Hello, " + name;
    }
}
