package com.example.bio.common.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Configuration
@ConfigurationProperties(prefix = "bio.iplimit")
public class IpLimitProperties {
    private Boolean enable = false;
    private Long limit = 100L;
    private Long timeout = 1000L;

    public Boolean getEnable() { return enable; }
    public void setEnable(Boolean enable) { this.enable = enable; }
    public Long getLimit() { return limit; }
    public void setLimit(Long limit) { this.limit = limit; }
    public Long getTimeout() { return timeout; }
    public void setTimeout(Long timeout) { this.timeout = timeout; }
}
