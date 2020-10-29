package com.example.bio.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.model.BioCategory;
import com.example.bio.mapper.BioCategoryMapper;
import com.example.bio.model.Biography;
import com.example.bio.service.BioCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.util.OrderItemUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<BioCategory> getAllCategoriesPage(PageQueryParams pageQueryParams) {
        QueryWrapper<BioCategory> queryWrapper = new QueryWrapper<>();
        Map<String, Object> conditions = pageQueryParams.getConditions();
        queryWrapper.eq("is_deleted", 0);
        IPage<BioCategory> page = page(pageQueryParams.getPage().addOrder(OrderItemUtil.orderHandler(conditions)));
        return page.getRecords();
    }
}
