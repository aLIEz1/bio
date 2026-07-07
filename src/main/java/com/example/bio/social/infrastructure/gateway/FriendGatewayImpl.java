package com.example.bio.social.infrastructure.gateway;

import com.example.bio.dto.AddFriendDto;
import com.example.bio.dto.UpdateFriendGroupDto;
import com.example.bio.model.UserFriendRelation;
import com.example.bio.service.UserFriendService;
import com.example.bio.social.domain.gateway.FriendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendGatewayImpl implements FriendGateway {

    private UserFriendService userFriendService;

    @Autowired
    public void setUserFriendService(UserFriendService userFriendService) {
        this.userFriendService = userFriendService;
    }

    @Override
    public void addFriend(AddFriendDto dto) {
        userFriendService.addFriend(dto);
    }

    @Override
    public void removeFriend(String friendId) {
        userFriendService.removeFriend(friendId);
    }

    @Override
    public List<UserFriendRelation> getFriendList() {
        return userFriendService.getFriendList();
    }

    @Override
    public void updateFriendGroup(UpdateFriendGroupDto dto) {
        userFriendService.updateFriendGroup(dto);
    }
}
