package com.example.bio.social.application.command;

import com.alibaba.cola.dto.Command;
import com.example.bio.model.EGroup;

import javax.validation.constraints.NotBlank;

public class AddFriendCmd extends Command {
    @NotBlank(message = "好友用户id不能为空")
    private String friendId;
    private EGroup userGroup = EGroup.GROUP_FRIEND;

    public String getFriendId() { return friendId; }
    public void setFriendId(String friendId) { this.friendId = friendId; }
    public EGroup getUserGroup() { return userGroup; }
    public void setUserGroup(EGroup userGroup) { this.userGroup = userGroup; }
}
