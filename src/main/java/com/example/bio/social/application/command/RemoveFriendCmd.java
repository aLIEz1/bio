package com.example.bio.social.application.command;

import com.alibaba.cola.dto.Command;

public class RemoveFriendCmd extends Command {
    private String friendId;

    public String getFriendId() { return friendId; }
    public void setFriendId(String friendId) { this.friendId = friendId; }
}
