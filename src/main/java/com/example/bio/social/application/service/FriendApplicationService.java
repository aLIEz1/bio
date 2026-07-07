package com.example.bio.social.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.example.bio.model.UserFriendRelation;
import com.example.bio.social.application.command.AddFriendCmd;
import com.example.bio.social.application.command.RemoveFriendCmd;
import com.example.bio.social.application.command.UpdateFriendGroupCmd;
import com.example.bio.social.application.executor.command.AddFriendCmdExe;
import com.example.bio.social.application.executor.command.RemoveFriendCmdExe;
import com.example.bio.social.application.executor.command.UpdateFriendGroupCmdExe;
import com.example.bio.social.application.executor.query.FriendListQryExe;
import com.example.bio.social.application.query.FriendListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendApplicationService {

    private AddFriendCmdExe addFriendExe;
    private RemoveFriendCmdExe removeFriendExe;
    private UpdateFriendGroupCmdExe updateFriendGroupExe;
    private FriendListQryExe friendListQryExe;

    @Autowired
    public void setAddFriendExe(AddFriendCmdExe addFriendExe) { this.addFriendExe = addFriendExe; }
    @Autowired
    public void setRemoveFriendExe(RemoveFriendCmdExe removeFriendExe) { this.removeFriendExe = removeFriendExe; }
    @Autowired
    public void setUpdateFriendGroupExe(UpdateFriendGroupCmdExe updateFriendGroupExe) { this.updateFriendGroupExe = updateFriendGroupExe; }
    @Autowired
    public void setFriendListQryExe(FriendListQryExe friendListQryExe) { this.friendListQryExe = friendListQryExe; }

    public Response addFriend(AddFriendCmd cmd) {
        return addFriendExe.execute(cmd);
    }

    public Response removeFriend(RemoveFriendCmd cmd) {
        return removeFriendExe.execute(cmd);
    }

    public MultiResponse<UserFriendRelation> getFriendList(FriendListQuery query) {
        return friendListQryExe.execute(query);
    }

    public Response updateFriendGroup(UpdateFriendGroupCmd cmd) {
        return updateFriendGroupExe.execute(cmd);
    }
}
