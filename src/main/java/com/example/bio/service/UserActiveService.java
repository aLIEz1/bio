package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.model.UserActive;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserActiveService extends IService<UserActive> {

    /**
     * 获取当前登录用户的活跃统计
     *
     * @return UserActive
     */
    UserActive getCurrentUserActive();

    /**
     * 根据userId获取指定用户活跃统计
     *
     * @param userId 用户id
     * @return UserActive
     */
    UserActive getUserActiveByUserId(String userId);

    /**
     * 注册成功后初始化用户活跃记录
     *
     * @param userId 用户id
     */
    void initUserActive(String userId);

    /**
     * 传记发布数量 +1
     *
     * @param userId 用户id
     */
    void incrementBioNum(String userId);

    /**
     * 评论数量 +1
     *
     * @param userId 用户id
     */
    void incrementCommentNum(String userId);
}
