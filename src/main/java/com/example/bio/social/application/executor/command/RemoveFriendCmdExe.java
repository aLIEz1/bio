package com.example.bio.social.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.social.application.command.RemoveFriendCmd;
import com.example.bio.social.domain.gateway.FriendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RemoveFriendCmdExe {

    private FriendGateway friendGateway;

    @Autowired
    public void setFriendGateway(FriendGateway friendGateway) { this.friendGateway = friendGateway; }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(RemoveFriendCmd cmd) {
        friendGateway.removeFriend(cmd.getFriendId());
        return Response.buildSuccess();
    }
}
