package com.example.bio.biography.application.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.command.*;
import com.example.bio.biography.application.executor.command.*;
import com.example.bio.biography.application.executor.query.*;
import com.example.bio.biography.application.query.BiographyByIdQuery;
import com.example.bio.biography.application.query.BiographyPageQuery;
import com.example.bio.biography.infrastructure.cache.BiographyLikeCache;
import org.springframework.stereotype.Service;

/**
 * 薄编排层：仅委托给对应 Executor，不含业务逻辑。
 */
@Service
public class BiographyApplicationService {

    private final CreateBiographyCmdExe createExe;
    private final UpdateBiographyCmdExe updateExe;
    private final DeleteBiographyCmdExe deleteExe;
    private final ToggleLikeCmdExe toggleLikeExe;
    private final BiographyPageQryExe pageQryExe;
    private final BiographyByIdQryExe byIdQryExe;
    private final BiographyLikeCache likeCache;

    public BiographyApplicationService(CreateBiographyCmdExe createExe,
                                       UpdateBiographyCmdExe updateExe,
                                       DeleteBiographyCmdExe deleteExe,
                                       ToggleLikeCmdExe toggleLikeExe,
                                       BiographyPageQryExe pageQryExe,
                                       BiographyByIdQryExe byIdQryExe,
                                       BiographyLikeCache likeCache) {
        this.createExe = createExe;
        this.updateExe = updateExe;
        this.deleteExe = deleteExe;
        this.toggleLikeExe = toggleLikeExe;
        this.pageQryExe = pageQryExe;
        this.byIdQryExe = byIdQryExe;
        this.likeCache = likeCache;
    }

    public Response create(CreateBiographyCmd cmd) {
        return createExe.execute(cmd);
    }

    public Response update(UpdateBiographyCmd cmd) {
        return updateExe.execute(cmd);
    }

    public Response delete(DeleteBiographyCmd cmd) {
        return deleteExe.execute(cmd);
    }

    public SingleResponse<Boolean> toggleLike(ToggleLikeCmd cmd) {
        return toggleLikeExe.execute(cmd);
    }

    public boolean hasLiked(String bioId, String userId) {
        if (userId == null) return false;
        return likeCache.isLiked(bioId, userId);
    }

    public PageResponse<BiographyVO> getMyBiographies(BiographyPageQuery query) {
        return pageQryExe.execute(query);
    }

    public PageResponse<BiographyVO> getPublicBiographies(BiographyPageQuery query) {
        return pageQryExe.execute(query);
    }

    public SingleResponse<BiographyVO> getMyBiographyById(BiographyByIdQuery query) {
        return byIdQryExe.execute(query);
    }

    public SingleResponse<BiographyVO> getPublicBiographyById(BiographyByIdQuery query) {
        return byIdQryExe.execute(query);
    }
}
