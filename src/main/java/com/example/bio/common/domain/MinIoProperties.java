package com.example.bio.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "min.io")
public class MinIoProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
