package com.example.bio.biography.infrastructure.gateway;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.gateway.PageResult;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.biography.domain.model.BiographyStatus;
import com.example.bio.biography.domain.model.PrivacyLevel;
import com.example.bio.biography.infrastructure.persistence.mapper.BiographyMapper;
import com.example.bio.biography.infrastructure.persistence.po.BiographyPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BiographyGatewayImpl
        extends ServiceImpl<BiographyMapper, BiographyPO>
        implements BiographyGateway {

    @Override
    public void save(Biography bio) {
        save(toPO(bio));
    }

    @Override
    public void update(Biography bio) {
        updateById(toPO(bio));
    }

    @Override
    public Optional<Biography> findById(String id) {
        QueryWrapper<BiographyPO> wrapper = new QueryWrapper<BiographyPO>()
                .eq("id", id).eq("is_deleted", 0);
        return Optional.ofNullable(getOne(wrapper)).map(this::toDomain);
    }

    @Override
    public void softDelete(String id) {
        UpdateWrapper<BiographyPO> wrapper = new UpdateWrapper<BiographyPO>()
                .eq("id", id).set("is_deleted", 1);
        update(wrapper);
    }

    @Override
    public void updateLikes(String id, boolean increment) {
        UpdateWrapper<BiographyPO> wrapper = new UpdateWrapper<BiographyPO>()
                .eq("id", id).eq("is_deleted", 0);
        if (increment) {
            wrapper.setSql("likes = likes + 1");
        } else {
            wrapper.setSql("likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END");
        }
        update(wrapper);
    }

    @Override
    public void incrementCommentNum(String id) {
        UpdateWrapper<BiographyPO> wrapper = new UpdateWrapper<BiographyPO>()
                .eq("id", id).eq("is_deleted", 0)
                .setSql("comment_num = comment_num + 1");
        update(wrapper);
    }

    @Override
    public PageResult<Biography> findByOwnerId(String ownerId, Map<String, Object> conditions,
                                               long current, long size) {
        QueryWrapper<BiographyPO> wrapper = new QueryWrapper<BiographyPO>()
                .eq("owner_id", ownerId).eq("is_deleted", 0);
        applyConditions(wrapper, conditions);
        IPage<BiographyPO> iPage = page(
                new Page<BiographyPO>(current, size).addOrder(OrderItem.desc("gmt_create")),
                wrapper);
        return toPageResult(iPage);
    }

    @Override
    public PageResult<Biography> findPublicBiographies(Map<String, Object> conditions,
                                                       long current, long size) {
        QueryWrapper<BiographyPO> wrapper = new QueryWrapper<BiographyPO>()
                .eq("privacy_level", 0).eq("status", 1).eq("is_deleted", 0);
        if (conditions != null) {
            Object categoryName = conditions.get("categoryName");
            Object ownerId = conditions.get("ownerId");
            if (categoryName != null) wrapper.eq("category_name", categoryName);
            if (ownerId != null) wrapper.eq("owner_id", ownerId);
        }
        IPage<BiographyPO> iPage = page(
                new Page<BiographyPO>(current, size).addOrder(OrderItem.desc("gmt_create")),
                wrapper);
        return toPageResult(iPage);
    }

    // ---- helpers ----

    private void applyConditions(QueryWrapper<BiographyPO> wrapper, Map<String, Object> conditions) {
        if (conditions == null) return;
        Object privacyLevel = conditions.get("privacyLevel");
        Object status = conditions.get("status");
        Object categoryName = conditions.get("categoryName");
        if (privacyLevel != null) wrapper.eq("privacy_level", privacyLevel);
        if (status != null) wrapper.eq("status", status);
        if (categoryName != null) wrapper.eq("category_name", categoryName);
    }

    private PageResult<Biography> toPageResult(IPage<BiographyPO> iPage) {
        List<Biography> records = iPage.getRecords().stream()
                .map(this::toDomain).collect(Collectors.toList());
        return new PageResult<>(records, iPage.getTotal(), iPage.getCurrent(), iPage.getSize());
    }

    Biography toDomain(BiographyPO po) {
        Biography b = new Biography();
        b.setId(po.getId());
        b.setOwnerId(po.getOwnerId());
        b.setTitle(po.getTitle());
        b.setContent(po.getContent());
        b.setCategoryId(po.getCategoryId());
        b.setCategoryName(po.getCategoryName());
        b.setPenName(po.getPenName());
        b.setPrivacyLevel(PrivacyLevel.of(po.getPrivacyLevel()));
        b.setStatus(BiographyStatus.of(po.getStatus()));
        b.setViews(po.getViews());
        b.setEnableComment(po.getEnableComment() == null ? 0 : po.getEnableComment());
        b.setNote(po.getNote());
        b.setCommentNum(po.getCommentNum() == null ? 0L : po.getCommentNum());
        b.setLikes(po.getLikes() == null ? 0L : po.getLikes());
        b.setGmtCreate(po.getGmtCreate());
        b.setGmtModified(po.getGmtModified());
        b.setDelFlag(po.getDelFlag() == null ? 0 : po.getDelFlag());
        return b;
    }

    private BiographyPO toPO(Biography b) {
        BiographyPO po = new BiographyPO();
        po.setId(b.getId());
        po.setOwnerId(b.getOwnerId());
        po.setTitle(b.getTitle());
        po.setContent(b.getContent());
        po.setCategoryId(b.getCategoryId());
        po.setCategoryName(b.getCategoryName());
        po.setPenName(b.getPenName());
        po.setPrivacyLevel(b.getPrivacyLevel() == null ? null : b.getPrivacyLevel().getCode());
        po.setStatus(b.getStatus() == null ? null : b.getStatus().getCode());
        po.setViews(b.getViews());
        po.setEnableComment(b.getEnableComment());
        po.setNote(b.getNote());
        po.setCommentNum(b.getCommentNum());
        po.setLikes(b.getLikes());
        po.setDelFlag(b.getDelFlag());
        return po;
    }
}
