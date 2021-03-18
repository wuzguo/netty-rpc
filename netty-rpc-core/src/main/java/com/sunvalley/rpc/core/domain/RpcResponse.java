package com.sunvalley.rpc.core.domain;

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
 * @date 2021/3/17 14:47
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse implements Serializable {

    /**
     * 请求ID
     */
    private Long requestId;

    /**
     * 返回值
     */
    private Object value;
}
