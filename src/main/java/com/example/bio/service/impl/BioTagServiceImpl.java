package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.mapper.BioTagMapper;
import com.example.bio.model.BioTag;
import com.example.bio.service.BioTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class BioTagServiceImpl extends ServiceImpl<BioTagMapper, BioTag> implements BioTagService {

    @Autowired
    private BioTagMapper tagMapper;

    @Override
    public List<BioTag> getTagsPage(PageQueryParams pageQueryParams) {
        QueryWrapper<BioTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        IPage<BioTag> page = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), queryWrapper);
        return page.getRecords();
    }

    @Override
    public void addBiographyTags(String bioId, Set<BioTag> tags) {
        tagMapper.addBiographyTags(bioId, tags);
    }

    @Override
    public Set<BioTag> getTagsByBiographyId(String id) {
        return tagMapper.getTagsByBiographyId(id);
    }

    @Override
    public Map<String, Set<BioTag>> getTagsByBiographyIds(List<String> bioIds) {
        if (bioIds == null || bioIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Map<String, Object>> rows = tagMapper.getTagsByBiographyIds(bioIds);
        Map<String, Set<BioTag>> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String bioId = String.valueOf(row.get("bio_id"));
            BioTag tag = new BioTag();
            tag.setId(String.valueOf(row.get("id")));
            tag.setTagName((String) row.get("tag_name"));
            Object delFlag = row.get("is_deleted");
            if (delFlag instanceof Number) {
                tag.setDelFlag(((Number) delFlag).intValue());
            }
            Object gmtCreate = row.get("gmt_create");
            if (gmtCreate instanceof java.util.Date) {
                tag.setGmtCreate((java.util.Date) gmtCreate);
            }
            Object gmtModified = row.get("gmt_modified");
            if (gmtModified instanceof java.util.Date) {
                tag.setGmtModified((java.util.Date) gmtModified);
            }
            result.computeIfAbsent(bioId, k -> new HashSet<>()).add(tag);
        }
        return result;
    }
}
