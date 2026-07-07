package com.example.bio.user.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.user.application.command.UpdateUserInfoCmd;
import com.example.bio.user.domain.gateway.UserInfoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserInfoCmdExe {

    private UserInfoGateway userInfoGateway;

    @Autowired
    public void setUserInfoGateway(UserInfoGateway userInfoGateway) {
        this.userInfoGateway = userInfoGateway;
    }

    public Response execute(UpdateUserInfoCmd cmd) {
        userInfoGateway.updateUserInfo(cmd.getDto());
        return Response.buildSuccess();
    }
}
