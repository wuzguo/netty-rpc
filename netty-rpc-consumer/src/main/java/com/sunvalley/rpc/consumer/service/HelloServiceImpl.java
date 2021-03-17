package com.sunvalley.rpc.consumer.service;

import com.sunvalley.rpc.facade.service.IHelloService;
import org.springframework.stereotype.Service;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 17:57
 */

@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String greet(String name) {
        return null;
    }
}
