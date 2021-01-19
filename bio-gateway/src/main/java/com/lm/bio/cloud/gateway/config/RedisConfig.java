package com.lm.bio.cloud.gateway.config;

import com.lm.bio.cloud.common.redis.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2021/1/1
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
