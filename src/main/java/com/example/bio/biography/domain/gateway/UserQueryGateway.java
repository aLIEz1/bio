package com.example.bio.biography.domain.gateway;

/**
 * Biography 上下文对 User 上下文的防腐接口（COLA Gateway）。
 */
public interface UserQueryGateway {

    void incrementBioNum(String userId);

    void incrementCommentNum(String userId);
}
