package com.example.bio.user.application.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.model.User;
import com.example.bio.user.application.query.UserByIdQuery;
import com.example.bio.user.domain.gateway.UserGateway;
import com.example.bio.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserByIdQryExe {

    private UserGateway userGateway;

    @Autowired
    public void setUserGateway(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public SingleResponse<UserVo> execute(UserByIdQuery query) {
        User user = userGateway.getById(query.getUserId());
        if (user == null) {
            return SingleResponse.buildFailure("USER_NOT_FOUND", "用户不存在");
        }
        UserVo vo = new UserVo(
                user.getId(), user.getUsername(), user.getAvatar(),
                user.getEmail(), user.getInvitationCode(), user.getPoints(),
                user.getIsLocked(), user.getRoles());
        return SingleResponse.of(vo);
    }

    public SingleResponse<UserVo> executeCurrentUser() {
        User user = userGateway.getCurrentUser();
        if (user == null) {
            return SingleResponse.buildFailure("NOT_LOGIN", "未登录");
        }
        UserVo vo = new UserVo(
                user.getId(), user.getUsername(), user.getAvatar(),
                user.getEmail(), user.getInvitationCode(), user.getPoints(),
                user.getIsLocked(), user.getRoles());
        return SingleResponse.of(vo);
    }
}
