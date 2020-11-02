package com.example.bio.config;

import com.example.bio.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/2
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
