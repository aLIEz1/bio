package com.example.bio.biography.infrastructure.gateway;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.biography.domain.gateway.BioCommentGateway;
import com.example.bio.biography.domain.model.BioComment;
import com.example.bio.biography.infrastructure.persistence.mapper.BioCommentMapper;
import com.example.bio.biography.infrastructure.persistence.po.BioCommentPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BioCommentGatewayImpl
        extends ServiceImpl<BioCommentMapper, BioCommentPO>
        implements BioCommentGateway {

    @Override
    public void save(BioComment comment) {
        save(toPO(comment));
    }

    @Override
    public Optional<BioComment> findById(String id) {
        return Optional.ofNullable(getById(id)).map(this::toDomain);
    }

    @Override
    public List<BioComment> findApprovedByBioId(String bioId) {
        return baseMapper.getCommentsByBioId(bioId).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<BioComment> findPending(long current, long size) {
        QueryWrapper<BioCommentPO> wrapper = new QueryWrapper<BioCommentPO>()
                .eq("comment_status", 0).eq("is_deleted", 0);
        Page<BioCommentPO> page = page(
                new Page<BioCommentPO>(current, size).addOrder(OrderItem.desc("gmt_create")),
                wrapper);
        return page.getRecords().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteByOwner(String commentId, String userId) {
        baseMapper.deleteCommentById(commentId, userId);
    }

    @Override
    public void deleteByBioOwner(String commentId, String bioOwnerId) {
        baseMapper.bioOwnerDeleteCommentById(commentId, bioOwnerId);
    }

    @Override
    public void softDelete(String id) {
        UpdateWrapper<BioCommentPO> wrapper = new UpdateWrapper<BioCommentPO>()
                .eq("id", id).set("is_deleted", 1);
        update(wrapper);
    }

    @Override
    public void approve(String id) {
        UpdateWrapper<BioCommentPO> wrapper = new UpdateWrapper<BioCommentPO>()
                .eq("id", id).eq("is_deleted", 0).set("comment_status", 1);
        update(wrapper);
    }

    @Override
    public void approveBatch(List<String> ids) {
        UpdateWrapper<BioCommentPO> wrapper = new UpdateWrapper<BioCommentPO>()
                .in("id", ids).eq("is_deleted", 0).set("comment_status", 1);
        update(wrapper);
    }

    BioComment toDomain(BioCommentPO po) {
        BioComment c = new BioComment();
        c.setId(po.getId());
        c.setBioId(po.getBioId());
        c.setUserId(po.getUserId());
        c.setCommentBody(po.getCommentBody());
        c.setCommentStatus(po.getCommentStatus() == null ? 0 : po.getCommentStatus());
        c.setParentId(po.getParentId());
        c.setGmtCreate(po.getGmtCreate());
        c.setGmtModified(po.getGmtModified());
        c.setDelFlag(po.getDelFlag() == null ? 0 : po.getDelFlag());
        return c;
    }

    private BioCommentPO toPO(BioComment c) {
        BioCommentPO po = new BioCommentPO();
        po.setId(c.getId());
        po.setBioId(c.getBioId());
        po.setUserId(c.getUserId());
        po.setCommentBody(c.getCommentBody());
        po.setCommentStatus(c.getCommentStatus());
        po.setParentId(c.getParentId());
        po.setDelFlag(c.getDelFlag());
        return po;
    }
}
