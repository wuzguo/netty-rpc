package com.sunvalley.rpc.facade.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 13:58
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String addr;

    /**
     * 联系电话
     */
    private String tel;
}
