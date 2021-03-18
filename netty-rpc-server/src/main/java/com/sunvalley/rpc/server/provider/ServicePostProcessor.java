package com.sunvalley.rpc.server.provider;

import com.sunvalley.rpc.core.annotation.RpcService;
import com.sunvalley.rpc.server.utils.BeanUtils;
import java.util.Optional;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 11:14
 */

@Component
public class ServicePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional.ofNullable(bean.getClass().getAnnotation(RpcService.class))
            .ifPresent(rpcService -> BeanUtils.put(rpcService.facade().getName(), rpcService.version(), bean));
        return bean;
    }
}
