package com.sunvalley.rpc.core.domain;

import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 14:31
 */

@Data
public class RpcRequest {

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 参数
     */
    private Object[] params;

    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;
}
