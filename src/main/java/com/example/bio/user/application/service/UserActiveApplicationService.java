package com.example.bio.user.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.model.UserActive;
import com.example.bio.user.application.executor.query.UserActiveQryExe;
import com.example.bio.user.application.query.UserActiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActiveApplicationService {

    private UserActiveQryExe userActiveQryExe;

    @Autowired
    public void setUserActiveQryExe(UserActiveQryExe userActiveQryExe) { this.userActiveQryExe = userActiveQryExe; }

    public SingleResponse<UserActive> getCurrentUserActive() {
        return userActiveQryExe.executeCurrentUser();
    }

    public SingleResponse<UserActive> getUserActiveByUserId(UserActiveQuery query) {
        return userActiveQryExe.execute(query);
    }
}
