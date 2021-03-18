package com.sunvalley.rpc.consumer.controller;

import com.sunvalley.rpc.core.annotation.RpcReference;
import com.sunvalley.rpc.facade.service.IHelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 17:41
 */

@Api("欢迎页面")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RpcReference
    private IHelloService helloService;

    @ApiOperation("问候语")
    @PostMapping("/greet")
    public String greet(@RequestParam String name) {
        return helloService.greet(name);
    }
}
