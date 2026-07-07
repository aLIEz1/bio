package com.example.bio.user.application.query;

import com.alibaba.cola.dto.Query;

public class UserInfoQuery extends Query {
    private String userId;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
