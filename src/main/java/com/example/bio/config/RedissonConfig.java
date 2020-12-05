package com.example.bio.config;

import com.example.bio.common.constant.CommonConstant;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config
                .useSingleServer()
                .setAddress(CommonConstant.REDISSON_ADDRESS)
                .setDatabase(0)
                .setDnsMonitoringInterval(50000L);
        return Redisson.create(config);
    }
}
