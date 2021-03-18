package com.sunvalley.rpc.server.service;

import com.sunvalley.rpc.core.annotation.RpcService;
import com.sunvalley.rpc.facade.service.IUserService;
import com.sunvalley.rpc.facade.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:12
 */

@Slf4j
@RpcService(facade = IUserService.class)
public class UserServiceImpl implements IUserService {

    @Override
    public UserVo find(Long id) {
        return UserVo.builder().id(id).email("zhang.san@gmail.com").nickName("san.zhang").addr("广东省深圳市南山区")
            .tel("0755-12345678").build();
    }

    @Override
    public void add(UserVo userVo) {
        log.info("user info: {} ", userVo);
    }
}
