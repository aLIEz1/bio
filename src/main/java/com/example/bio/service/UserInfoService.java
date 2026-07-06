package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.dto.UpdateUserInfoDto;
import com.example.bio.model.UserInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取当前登录用户的详细信息
     *
     * @return UserInfo
     */
    UserInfo getCurrentUserInfo();

    /**
     * 根据userId获取用户详细信息（供游客访问）
     *
     * @param userId 用户id
     * @return UserInfo
     */
    UserInfo getUserInfoByUserId(String userId);

    /**
     * 更新当前登录用户的详细信息
     *
     * @param dto 更新信息
     */
    void updateUserInfo(UpdateUserInfoDto dto);
}
