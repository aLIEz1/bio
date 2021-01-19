package com.lm.bio.cloud.admin.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangfuqi
 * @date 2020/12/21
 */
@SpringBootApplication(scanBasePackages = "com.lm")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
