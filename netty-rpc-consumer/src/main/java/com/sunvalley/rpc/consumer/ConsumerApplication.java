package com.sunvalley.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/17 17:41
 */

@EnableOpenApi
@EnableConfigurationProperties
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
