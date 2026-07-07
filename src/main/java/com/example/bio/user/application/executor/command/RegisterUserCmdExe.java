package com.example.bio.user.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.dto.SignupDto;
import com.example.bio.user.application.command.RegisterUserCmd;
import com.example.bio.user.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegisterUserCmdExe {

    private UserGateway userGateway;

    @Autowired
    public void setUserGateway(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(RegisterUserCmd cmd) {
        SignupDto dto = new SignupDto();
        dto.setUsername(cmd.getUsername());
        dto.setPassword(cmd.getPassword());
        dto.setEmail(cmd.getEmail());
        dto.setAuthCode(cmd.getAuthCode());
        userGateway.registerUser(dto);
        return Response.buildSuccess();
    }
}
