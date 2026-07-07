package com.example.bio.social.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.dto.AddFriendDto;
import com.example.bio.social.application.command.AddFriendCmd;
import com.example.bio.social.domain.gateway.FriendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddFriendCmdExe {

    private FriendGateway friendGateway;

    @Autowired
    public void setFriendGateway(FriendGateway friendGateway) { this.friendGateway = friendGateway; }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(AddFriendCmd cmd) {
        AddFriendDto dto = new AddFriendDto();
        dto.setFriendId(cmd.getFriendId());
        dto.setUserGroup(cmd.getUserGroup());
        friendGateway.addFriend(dto);
        return Response.buildSuccess();
    }
}
