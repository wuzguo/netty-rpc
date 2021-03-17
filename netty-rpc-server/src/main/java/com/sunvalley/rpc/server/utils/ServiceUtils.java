package com.sunvalley.rpc.server.utils;

import com.google.common.collect.Maps;
import java.util.Map;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 15:00
 */

@UtilityClass
public class ServiceUtils {

    /**
     * 记录当前服务中的接口对象
     */
    private static final Map<String, Map<String, Object>> mapServices = Maps.newConcurrentMap();

    /**
     * 添加Bean
     *
     * @param serviceName 服务名
     * @param version     版本号
     * @param bean        实例
     */
    public void put(String serviceName, String version, Object bean) {
        Map<String, Object> mapVersions = mapServices.getOrDefault(serviceName, Maps.newConcurrentMap());
        mapVersions.putIfAbsent(version, bean);
    }

    /**
     * 获取Bean对象
     *
     * @param serviceName 服务名称
     * @param version     版本号
     * @return {@link Object}
     */
    public Object get(String serviceName, String version) {
        return mapServices.getOrDefault(serviceName, Maps.newConcurrentMap()).get(version);
    }

}
