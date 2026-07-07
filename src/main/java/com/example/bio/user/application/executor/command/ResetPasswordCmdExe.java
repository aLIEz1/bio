package com.example.bio.user.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.dto.ResetPasswordDto;
import com.example.bio.user.application.command.ResetPasswordCmd;
import com.example.bio.user.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordCmdExe {

    private UserGateway userGateway;

    @Autowired
    public void setUserGateway(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Response execute(ResetPasswordCmd cmd) {
        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setEmailAddress(cmd.getEmailAddress());
        dto.setTokenSecret(cmd.getTokenSecret());
        dto.setPassword(cmd.getPassword());
        dto.setConfirmationPassword(cmd.getConfirmationPassword());
        userGateway.resetPassword(dto);
        return Response.buildSuccess();
    }
}
