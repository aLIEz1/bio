package com.example.bio.user.infrastructure.gateway;

import com.example.bio.model.UserActive;
import com.example.bio.service.UserActiveService;
import com.example.bio.user.domain.gateway.UserActiveGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActiveGatewayImpl implements UserActiveGateway {

    private UserActiveService userActiveService;

    @Autowired
    public void setUserActiveService(UserActiveService userActiveService) {
        this.userActiveService = userActiveService;
    }

    @Override
    public UserActive getCurrentUserActive() {
        return userActiveService.getCurrentUserActive();
    }

    @Override
    public UserActive getUserActiveByUserId(String userId) {
        return userActiveService.getUserActiveByUserId(userId);
    }

    @Override
    public void initUserActive(String userId) {
        userActiveService.initUserActive(userId);
    }

    @Override
    public void incrementBioNum(String userId) {
        userActiveService.incrementBioNum(userId);
    }

    @Override
    public void incrementCommentNum(String userId) {
        userActiveService.incrementCommentNum(userId);
    }
}
