package com.example.bio.user.infrastructure.gateway;

import com.example.bio.dto.UpdateUserInfoDto;
import com.example.bio.model.UserInfo;
import com.example.bio.service.UserInfoService;
import com.example.bio.user.domain.gateway.UserInfoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoGatewayImpl implements UserInfoGateway {

    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        return userInfoService.getCurrentUserInfo();
    }

    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return userInfoService.getUserInfoByUserId(userId);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoDto dto) {
        userInfoService.updateUserInfo(dto);
    }
}
