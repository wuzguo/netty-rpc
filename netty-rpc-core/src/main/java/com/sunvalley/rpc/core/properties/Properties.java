package com.sunvalley.rpc.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 16:42
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "rpc")
public class Properties {

    @Value("${rpc.server.port}")
    private Integer serverPort;

    private Registry registry;

    @Getter
    @Setter
    public static class Registry {

        private String type;

        private String addr;
    }
}
