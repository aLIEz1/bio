package com.lm.bio.cloud.gateway.security.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author zhangfuqi
 * @date 2021/1/1
 */
@Configuration
@ConfigurationProperties(prefix = "bio.security")
@Data
public class WhiteListProperties {
    private Set<String> noRequireTokenUris;
}
