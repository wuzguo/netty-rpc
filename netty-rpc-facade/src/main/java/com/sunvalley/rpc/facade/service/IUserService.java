package com.sunvalley.rpc.facade.service;

import com.sunvalley.rpc.facade.vo.UserBo;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:00
 */

public interface IUserService {

    /**
     * 查询用户信息
     *
     * @param id 用户ID
     * @return {@link UserBo}
     */
    UserBo find(Long id);
}
