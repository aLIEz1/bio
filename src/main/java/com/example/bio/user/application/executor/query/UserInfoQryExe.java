package com.example.bio.user.application.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.model.UserInfo;
import com.example.bio.user.application.query.UserInfoQuery;
import com.example.bio.user.domain.gateway.UserInfoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoQryExe {

    private UserInfoGateway userInfoGateway;

    @Autowired
    public void setUserInfoGateway(UserInfoGateway userInfoGateway) {
        this.userInfoGateway = userInfoGateway;
    }

    public SingleResponse<UserInfo> execute(UserInfoQuery query) {
        UserInfo userInfo = userInfoGateway.getUserInfoByUserId(query.getUserId());
        return SingleResponse.of(userInfo);
    }

    public SingleResponse<UserInfo> executeCurrentUser() {
        UserInfo userInfo = userInfoGateway.getCurrentUserInfo();
        return SingleResponse.of(userInfo);
    }
}
