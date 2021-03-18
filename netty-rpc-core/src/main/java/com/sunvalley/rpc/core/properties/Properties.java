package com.sunvalley.rpc.core.properties;

import lombok.Data;
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

    private Server server;

    private Registry registry;

    @Data
    public static class Server {

        private String hostname;

        private Integer port;

    }

    @Data
    public static class Registry {

        private String type;

        private String addr;
    }
}
