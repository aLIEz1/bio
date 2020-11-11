package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.mapper.BioCategoryMapper;
import com.example.bio.model.BioCategory;
import com.example.bio.service.BioCategoryCacheService;
import com.example.bio.service.BioCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class BioCategoryServiceImpl extends ServiceImpl<BioCategoryMapper, BioCategory> implements BioCategoryService {

    @Autowired
    private BioCategoryCacheService categoryCacheService;

    @Override
    public List<BioCategory> getAllCategoriesPage(PageQueryParams pageQueryParams) {
        QueryWrapper<BioCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        IPage<BioCategory> page = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), queryWrapper);
        return page.getRecords();
    }

    @Override
    public BioCategory getById(Serializable id) {
        BioCategory bioCategory = categoryCacheService.getCategory((String) id);
        if (bioCategory != null) {
            return bioCategory;
        } else {
            BioCategory byId = super.getById(id);
            if (byId.getDelFlag() == 1) {
                return null;
            }
            categoryCacheService.setCategory(byId);
            return byId;
        }
    }
}
