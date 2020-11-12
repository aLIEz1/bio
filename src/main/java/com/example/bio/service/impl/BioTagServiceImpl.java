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

import java.util.List;
import java.util.Set;

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
}
