package com.example.bio.social.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.dto.UpdateFriendGroupDto;
import com.example.bio.social.application.command.UpdateFriendGroupCmd;
import com.example.bio.social.domain.gateway.FriendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateFriendGroupCmdExe {

    private FriendGateway friendGateway;

    @Autowired
    public void setFriendGateway(FriendGateway friendGateway) { this.friendGateway = friendGateway; }

    public Response execute(UpdateFriendGroupCmd cmd) {
        UpdateFriendGroupDto dto = new UpdateFriendGroupDto();
        dto.setFriendId(cmd.getFriendId());
        dto.setUserGroup(cmd.getUserGroup());
        friendGateway.updateFriendGroup(dto);
        return Response.buildSuccess();
    }
}
