package com.example.bio.social.domain.gateway;

import com.example.bio.dto.AddFriendDto;
import com.example.bio.dto.UpdateFriendGroupDto;
import com.example.bio.model.UserFriendRelation;

import java.util.List;

public interface FriendGateway {
    void addFriend(AddFriendDto dto);
    void removeFriend(String friendId);
    List<UserFriendRelation> getFriendList();
    void updateFriendGroup(UpdateFriendGroupDto dto);
}
