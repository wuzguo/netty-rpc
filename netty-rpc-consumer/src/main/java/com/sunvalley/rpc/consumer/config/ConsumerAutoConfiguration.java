package com.sunvalley.rpc.consumer.config;

import com.sunvalley.rpc.consumer.pool.NettyPool;
import com.sunvalley.rpc.core.properties.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/18 13:44
 */

@Configuration
@EnableConfigurationProperties(Properties.class)
public class ConsumerAutoConfiguration {

    @Autowired
    private Properties properties;

    @Bean
    public NettyPool nettyPool() {
        return NettyPool.getPool(properties.getServer().getHostname(), properties.getServer().getPort());
    }
}
