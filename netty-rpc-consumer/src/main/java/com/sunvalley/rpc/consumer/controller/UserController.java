package com.sunvalley.rpc.consumer.controller;

import com.sunvalley.rpc.core.annotation.RpcReference;
import com.sunvalley.rpc.facade.service.IUserService;
import com.sunvalley.rpc.facade.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 16:12
 */

@Api("用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @RpcReference
    private IUserService userService;


    @ApiOperation("查询")
    @GetMapping("/get")
    public UserVo get(@RequestParam Long id) {
        return userService.find(id);
    }

    @ApiOperation("保存")
    @PostMapping("/add")
    public void add(@RequestBody UserVo userVo) {
        userService.add(userVo);
    }
}
