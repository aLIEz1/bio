package com.example.bio.user.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.model.UserInfo;
import com.example.bio.user.application.command.UpdateUserInfoCmd;
import com.example.bio.user.application.executor.command.UpdateUserInfoCmdExe;
import com.example.bio.user.application.executor.query.UserInfoQryExe;
import com.example.bio.user.application.query.UserInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoApplicationService {

    private UpdateUserInfoCmdExe updateUserInfoExe;
    private UserInfoQryExe userInfoQryExe;

    @Autowired
    public void setUpdateUserInfoExe(UpdateUserInfoCmdExe updateUserInfoExe) { this.updateUserInfoExe = updateUserInfoExe; }
    @Autowired
    public void setUserInfoQryExe(UserInfoQryExe userInfoQryExe) { this.userInfoQryExe = userInfoQryExe; }

    public SingleResponse<UserInfo> getCurrentUserInfo() {
        return userInfoQryExe.executeCurrentUser();
    }

    public SingleResponse<UserInfo> getUserInfoByUserId(UserInfoQuery query) {
        return userInfoQryExe.execute(query);
    }

    public Response updateUserInfo(UpdateUserInfoCmd cmd) {
        return updateUserInfoExe.execute(cmd);
    }
}
