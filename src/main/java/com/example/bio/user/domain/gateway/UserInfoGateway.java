package com.example.bio.user.domain.gateway;

import com.example.bio.dto.UpdateUserInfoDto;
import com.example.bio.model.UserInfo;

public interface UserInfoGateway {
    UserInfo getCurrentUserInfo();
    UserInfo getUserInfoByUserId(String userId);
    void updateUserInfo(UpdateUserInfoDto dto);
}
