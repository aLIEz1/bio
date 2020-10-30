package com.example.bio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User getOneByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    User getOneByEmail(@Param("email") String email);

    /**
     * 解锁用户
     *
     * @param user
     */
    void unlockUser(User user);

}
