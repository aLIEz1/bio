package com.example.bio.user.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.user.application.command.RegisterUserCmd;
import com.example.bio.user.application.command.ResetPasswordCmd;
import com.example.bio.user.application.executor.command.RegisterUserCmdExe;
import com.example.bio.user.application.executor.command.ResetPasswordCmdExe;
import com.example.bio.user.application.executor.query.UserByIdQryExe;
import com.example.bio.user.application.query.UserByIdQuery;
import com.example.bio.user.domain.gateway.UserGateway;
import com.example.bio.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private RegisterUserCmdExe registerExe;
    private ResetPasswordCmdExe resetPasswordExe;
    private UserByIdQryExe userByIdQryExe;
    private UserGateway userGateway;

    @Autowired
    public void setRegisterExe(RegisterUserCmdExe registerExe) { this.registerExe = registerExe; }
    @Autowired
    public void setResetPasswordExe(ResetPasswordCmdExe resetPasswordExe) { this.resetPasswordExe = resetPasswordExe; }
    @Autowired
    public void setUserByIdQryExe(UserByIdQryExe userByIdQryExe) { this.userByIdQryExe = userByIdQryExe; }
    @Autowired
    public void setUserGateway(UserGateway userGateway) { this.userGateway = userGateway; }

    public Response register(RegisterUserCmd cmd) {
        return registerExe.execute(cmd);
    }

    public Response resetPassword(ResetPasswordCmd cmd) {
        return resetPasswordExe.execute(cmd);
    }

    public SingleResponse<UserVo> getCurrentUser() {
        return userByIdQryExe.executeCurrentUser();
    }

    public SingleResponse<UserVo> getUserById(UserByIdQuery query) {
        return userByIdQryExe.execute(query);
    }

    public boolean unlockUser(String token) {
        return userGateway.unlockUser(token);
    }

    public String generateAuthCode(String email) {
        return userGateway.generateAuthCode(email);
    }

    public void sendResetPasswordToken(String email) {
        userGateway.getResetPasswordToken(email);
    }
}
