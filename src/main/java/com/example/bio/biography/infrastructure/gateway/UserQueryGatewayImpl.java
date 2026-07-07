package com.example.bio.biography.infrastructure.gateway;

import com.example.bio.biography.domain.gateway.UserQueryGateway;
import com.example.bio.service.UserActiveService;
import org.springframework.stereotype.Component;

/**
 * UserQueryGateway 防腐层适配器：将 biography 上下文的跨界需求委托给旧 UserActiveService。
 * user 上下文迁移完成后，此处改为调用 user 限界上下文的 ApplicationService。
 */
@Component
public class UserQueryGatewayImpl implements UserQueryGateway {

    private final UserActiveService userActiveService;

    public UserQueryGatewayImpl(UserActiveService userActiveService) {
        this.userActiveService = userActiveService;
    }

    @Override
    public void incrementBioNum(String userId) {
        userActiveService.incrementBioNum(userId);
    }

    @Override
    public void incrementCommentNum(String userId) {
        userActiveService.incrementCommentNum(userId);
    }
}
