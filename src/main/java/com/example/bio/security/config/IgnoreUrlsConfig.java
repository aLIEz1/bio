package com.example.bio.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
    private List<String> limitUrls = new ArrayList<>();

    public List<String> getUrls() { return urls; }
    public void setUrls(List<String> urls) { this.urls = urls; }
    public List<String> getLimitUrls() { return limitUrls; }
    public void setLimitUrls(List<String> limitUrls) { this.limitUrls = limitUrls; }
}
