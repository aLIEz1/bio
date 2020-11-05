package com.example.bio.common.limiter;

import com.example.bio.common.constant.CommonConstant;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的Token Bucket令牌桶算法限流
 *
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Slf4j
@Component
public class RedisRaterLimiter {

    @Autowired
    private RedissonClient redissonClient;

    public Boolean acquireByRedis(String name, Long rate, Long rateInterval) {

        RRateLimiter rateLimiter = redissonClient.getRateLimiter(CommonConstant.LIMIT_PRE + name);
        rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.MILLISECONDS);
        rateLimiter.expire(rateInterval, TimeUnit.MILLISECONDS);

        return rateLimiter.tryAcquire();
    }


}
