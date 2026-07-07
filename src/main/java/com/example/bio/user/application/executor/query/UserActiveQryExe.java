package com.example.bio.user.application.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.model.UserActive;
import com.example.bio.user.application.query.UserActiveQuery;
import com.example.bio.user.domain.gateway.UserActiveGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActiveQryExe {

    private UserActiveGateway userActiveGateway;

    @Autowired
    public void setUserActiveGateway(UserActiveGateway userActiveGateway) {
        this.userActiveGateway = userActiveGateway;
    }

    public SingleResponse<UserActive> execute(UserActiveQuery query) {
        UserActive userActive = userActiveGateway.getUserActiveByUserId(query.getUserId());
        return SingleResponse.of(userActive);
    }

    public SingleResponse<UserActive> executeCurrentUser() {
        UserActive userActive = userActiveGateway.getCurrentUserActive();
        return SingleResponse.of(userActive);
    }
}
