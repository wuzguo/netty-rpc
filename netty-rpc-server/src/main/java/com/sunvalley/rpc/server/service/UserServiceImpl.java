package com.sunvalley.rpc.server.service;

import com.sunvalley.rpc.core.annotation.RpcService;
import com.sunvalley.rpc.facade.service.IHelloService;
import com.sunvalley.rpc.facade.service.IUserService;
import com.sunvalley.rpc.facade.vo.UserBo;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:12
 */

@RpcService(facade = IHelloService.class)
public class UserServiceImpl implements IUserService {

    @Override
    public UserBo find(Long id) {
        return UserBo.builder().id(id).email("zhang.san@gmail.com").nickName("san.zhang").addr("广东省深圳市南山区")
            .tel("0755-12345678").build();
    }
}
