package com.sunvalley.rpc.consumer.proxy;

import com.google.common.collect.Maps;
import com.sunvalley.rpc.consumer.pool.NettyPool;
import com.sunvalley.rpc.core.annotation.RpcReference;
import com.sunvalley.rpc.core.properties.Properties;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 15:30
 */

@Slf4j
@Component
public class ConsumerPostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ApplicationContext context;

    private ClassLoader classLoader;


    private final Map<String, BeanDefinition> refBeanDefinitions = Maps.newHashMap();


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);
            }
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.refBeanDefinitions.forEach((beanName, beanDefinition) -> {
            if (context.containsBean(beanName)) {
                throw new IllegalArgumentException("spring context already has a bean named " + beanName);
            }
            registry.registerBeanDefinition(beanName, refBeanDefinitions.get(beanName));
            log.info("registered reference bean {} success.", beanName);
        });
    }


    private void parseRpcReference(Field field) {
        Optional.ofNullable(AnnotationUtils.getAnnotation(field, RpcReference.class)).ifPresent(reference -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ReferenceFactoryBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("version", reference.version());
            builder.addPropertyValue("timeout", reference.timeout());
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            refBeanDefinitions.put(field.getName(), beanDefinition);
        });
    }
}
