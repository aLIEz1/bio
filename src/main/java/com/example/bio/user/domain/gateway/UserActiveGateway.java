package com.example.bio.user.domain.gateway;

import com.example.bio.model.UserActive;

public interface UserActiveGateway {
    UserActive getCurrentUserActive();
    UserActive getUserActiveByUserId(String userId);
    void initUserActive(String userId);
    void incrementBioNum(String userId);
    void incrementCommentNum(String userId);
}
