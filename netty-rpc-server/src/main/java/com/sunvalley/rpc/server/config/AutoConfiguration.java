package com.sunvalley.rpc.server.config;

import com.sunvalley.rpc.core.properties.Properties;
import com.sunvalley.rpc.server.provider.ServerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 16:43
 */

@Configuration
@EnableConfigurationProperties(Properties.class)
public class AutoConfiguration {

    @Autowired
    private Properties properties;

    @Bean
    public ServerProvider initialize() {
        return new ServerProvider(properties.getServerPort());
    }
}
