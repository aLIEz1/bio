package com.example.bio.social.application.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.example.bio.model.UserFriendRelation;
import com.example.bio.social.application.query.FriendListQuery;
import com.example.bio.social.domain.gateway.FriendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendListQryExe {

    private FriendGateway friendGateway;

    @Autowired
    public void setFriendGateway(FriendGateway friendGateway) { this.friendGateway = friendGateway; }

    public MultiResponse<UserFriendRelation> execute(FriendListQuery query) {
        List<UserFriendRelation> list = friendGateway.getFriendList();
        return MultiResponse.of(list);
    }
}
