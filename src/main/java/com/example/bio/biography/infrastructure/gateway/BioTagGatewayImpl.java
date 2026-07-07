package com.example.bio.biography.infrastructure.gateway;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.biography.domain.gateway.BioTagGateway;
import com.example.bio.biography.domain.model.BioTag;
import com.example.bio.biography.infrastructure.persistence.mapper.BioTagMapper;
import com.example.bio.biography.infrastructure.persistence.po.BioTagPO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BioTagGatewayImpl
        extends ServiceImpl<BioTagMapper, BioTagPO>
        implements BioTagGateway {

    @Override
    public void save(BioTag tag) {
        save(toPO(tag));
    }

    @Override
    public Optional<BioTag> findById(String id) {
        QueryWrapper<BioTagPO> wrapper = new QueryWrapper<BioTagPO>()
                .eq("id", id).eq("is_deleted", 0);
        return Optional.ofNullable(getOne(wrapper)).map(this::toDomain);
    }

    @Override
    public void softDelete(String id) {
        UpdateWrapper<BioTagPO> wrapper = new UpdateWrapper<BioTagPO>()
                .eq("id", id).set("is_deleted", 1);
        update(wrapper);
    }

    @Override
    public List<BioTag> findPage(long current, long size) {
        QueryWrapper<BioTagPO> wrapper = new QueryWrapper<BioTagPO>().eq("is_deleted", 0);
        Page<BioTagPO> page = page(
                new Page<BioTagPO>(current, size).addOrder(OrderItem.desc("gmt_create")),
                wrapper);
        return page.getRecords().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void bindTagsToBiography(String bioId, Set<String> tagIds) {
        baseMapper.addBiographyTags(bioId, tagIds);
    }

    @Override
    public Set<BioTag> findByBiographyId(String bioId) {
        return baseMapper.getTagsByBiographyId(bioId).stream()
                .map(this::toDomain).collect(Collectors.toSet());
    }

    @Override
    public Map<String, Set<BioTag>> findByBiographyIds(List<String> bioIds) {
        if (bioIds == null || bioIds.isEmpty()) return Collections.emptyMap();
        List<Map<String, Object>> rows = baseMapper.getTagsByBiographyIds(bioIds);
        Map<String, Set<BioTag>> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String bioId = String.valueOf(row.get("bio_id"));
            BioTag tag = new BioTag();
            tag.setId(String.valueOf(row.get("id")));
            tag.setTagName((String) row.get("tag_name"));
            Object delFlag = row.get("is_deleted");
            if (delFlag instanceof Number) tag.setDelFlag(((Number) delFlag).intValue());
            Object gmtCreate = row.get("gmt_create");
            if (gmtCreate instanceof Date) tag.setGmtCreate((Date) gmtCreate);
            Object gmtModified = row.get("gmt_modified");
            if (gmtModified instanceof Date) tag.setGmtModified((Date) gmtModified);
            result.computeIfAbsent(bioId, k -> new HashSet<>()).add(tag);
        }
        return result;
    }

    @Override
    public List<BioTag> findByIds(Set<String> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        QueryWrapper<BioTagPO> wrapper = new QueryWrapper<BioTagPO>()
                .in("id", ids).eq("is_deleted", 0);
        return list(wrapper).stream().map(this::toDomain).collect(Collectors.toList());
    }

    BioTag toDomain(BioTagPO po) {
        BioTag t = new BioTag();
        t.setId(po.getId());
        t.setTagName(po.getTagName());
        t.setGmtCreate(po.getGmtCreate());
        t.setGmtModified(po.getGmtModified());
        t.setDelFlag(po.getDelFlag() == null ? 0 : po.getDelFlag());
        return t;
    }

    private BioTagPO toPO(BioTag t) {
        BioTagPO po = new BioTagPO();
        po.setId(t.getId());
        po.setTagName(t.getTagName());
        po.setDelFlag(t.getDelFlag());
        return po;
    }
}
