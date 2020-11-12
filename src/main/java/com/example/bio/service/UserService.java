package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.dto.SignupDto;
import com.example.bio.model.User;
import org.springframework.transaction.annotation.Transactional;

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
     * 解锁用户
     *
     * @param token
     * @return
     */
    @Transactional(rollbackFor = {Error.class, RuntimeException.class})
    boolean unlockUser(String token);


    /**
     * 注册用户
     *
     * @param signupDto
     */
    @Transactional(rollbackFor = {Error.class, RuntimeException.class})
    void registerUser(SignupDto signupDto);

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    User getCurrentUser();

    /**
     * 生成验证码
     *
     * @param email
     * @return
     */
    String generateAuthCode(String email);

}
