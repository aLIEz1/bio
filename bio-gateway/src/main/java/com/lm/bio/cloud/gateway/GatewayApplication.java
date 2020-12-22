package com.lm.bio.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangfuqi
 * @date 2020/12/22
 */
@SpringBootApplication(scanBasePackages = "com.lm")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
