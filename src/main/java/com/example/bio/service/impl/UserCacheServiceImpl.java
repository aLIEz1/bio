package com.example.bio.service.impl;

import com.example.bio.common.annotation.CacheException;
import com.example.bio.model.User;
import com.example.bio.service.RedisService;
import com.example.bio.service.UserCacheService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangfuqi
 * @date 2020/11/2
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.user}")
    private String REDIS_KEY_USER;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Override
    public void deleteUserCache(String username) {
        User user = userService.getOneByUsername(username);
        if (user != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + username;
            redisService.del(key);
        }
    }

    @Override
    public User getUser(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + username;
        return (User) redisService.get(key);
    }

    @Override
    public void setUser(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + user.getUsername();
        redisService.set(key, user, REDIS_EXPIRE);
    }

    @CacheException
    @Override
    public void setAuthCode(String emial, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + emial;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    @CacheException
    @Override
    public String getAuthCode(String email) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + email;
        return (String) redisService.get(key);
    }
}
