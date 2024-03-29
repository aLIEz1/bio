package com.example.bio.service;

import com.example.bio.model.User;

/**
 * @author zhangfuqi
 * @date 2020/11/2
 */
public interface UserCacheService {
    /**
     * 删除用户缓存
     *
     * @param username
     */
    void deleteUserCache(String username);

    /**
     * 从缓存中获取用户
     *
     * @param username
     * @return
     */
    User getUser(String username);

    /**
     * 向缓存中存入用户
     *
     * @param user
     */
    void setUser(User user);

    /**
     * 向缓存中存放auth code
     *
     * @param email
     * @param authCode
     */
    void setAuthCode(String email, String authCode);

    /**
     * 从缓存中获取auth code
     *
     * @param email
     * @return
     */
    String getAuthCode(String email);

    /**
     * 向缓存中存放重置密码的token
     *
     * @param email
     * @param token
     */
    void setResetPasswordToken(String email, String token);

    /**
     * 从缓存中取出重置密码的token
     *
     * @param email
     * @return
     */
    String getResetPasswordToken(String email);

    /**
     * 从缓存中移除重置密码token
     *
     * @param email
     */
    void deleteResetPasswordToken(String email);
}
