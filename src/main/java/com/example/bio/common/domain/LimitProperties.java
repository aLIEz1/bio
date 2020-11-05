package com.example.bio.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bio.ratelimit")
public class LimitProperties {
    /**
     * 是否开启全局限流
     */
    private Boolean enable = false;

    /**
     * 限制请求个数
     */
    private Long limit = 100L;

    /**
     * 每单位时间内（毫秒）
     */
    private Long timeout = 1000L;
}
