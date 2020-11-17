package com.example.bio.service.impl;

import com.example.bio.common.annotation.CacheException;
import com.example.bio.common.constant.CacheConstant;
import com.example.bio.model.User;
import com.example.bio.service.RedisService;
import com.example.bio.service.UserCacheService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void deleteUserCache(String username) {
        User user = userService.getOneByUsername(username);
        if (user != null) {
            String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_USER + ":" + username;
            redisService.del(key);
        }
    }

    @Override
    public User getUser(String username) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_USER + ":" + username;
        return (User) redisService.get(key);
    }

    @Override
    public void setUser(User user) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_USER + ":" + user.getUsername();
        redisService.set(key, user, CacheConstant.REDIS_EXPIRE);
    }

    @CacheException
    @Override
    public void setAuthCode(String email, String authCode) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_AUTH_CODE + ":" + email;
        redisService.set(key, authCode, CacheConstant.REDIS_EXPIRE_AUTH_CODE);
    }

    @CacheException
    @Override
    public String getAuthCode(String email) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_AUTH_CODE + ":" + email;
        return (String) redisService.get(key);
    }

    @CacheException
    @Override
    public void setResetPasswordToken(String email, String token) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_RESET_PASSWORD + ":" + email;
        redisService.set(key, token, CacheConstant.REDIS_EXPIRE_RESET_PASSWORD);
    }

    @CacheException
    @Override
    public String getResetPasswordToken(String email) {
        String key = CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_RESET_PASSWORD + ":" + email;
        return (String) redisService.get(key);
    }
}
