package com.sunvalley.rpc.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 17:59
 */

@Data
@ApiModel("问候请求对象")
public class ReqGreetVo {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
}
