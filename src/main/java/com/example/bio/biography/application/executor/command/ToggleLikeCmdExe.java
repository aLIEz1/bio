package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.command.ToggleLikeCmd;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.infrastructure.cache.BiographyLikeCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ToggleLikeCmdExe {

    private final BiographyGateway biographyGateway;
    private final BiographyLikeCache likeCache;

    public ToggleLikeCmdExe(BiographyGateway biographyGateway,
                            BiographyLikeCache likeCache) {
        this.biographyGateway = biographyGateway;
        this.likeCache = likeCache;
    }

    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<Boolean> execute(ToggleLikeCmd cmd) {
        boolean alreadyLiked = likeCache.isLiked(cmd.getBioId(), cmd.getCurrentUserId());
        if (alreadyLiked) {
            likeCache.unlike(cmd.getBioId(), cmd.getCurrentUserId());
            biographyGateway.updateLikes(cmd.getBioId(), false);
            return SingleResponse.of(false);
        } else {
            likeCache.like(cmd.getBioId(), cmd.getCurrentUserId());
            biographyGateway.updateLikes(cmd.getBioId(), true);
            return SingleResponse.of(true);
        }
    }
}
