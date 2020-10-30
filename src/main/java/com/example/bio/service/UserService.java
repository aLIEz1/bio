package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.dto.SignupDto;
import com.example.bio.model.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User getOneByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    User getOneByEmail(String email);

    /**
     * 解锁用户
     *
     * @param token
     * @return
     */
    boolean unlockUser(String token);


    /**
     * 注册用户
     *
     * @param signupDto
     */
    void registerUser(SignupDto signupDto);

}
