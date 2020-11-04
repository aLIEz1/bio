package com.example.bio.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.BiographyMapper;
import com.example.bio.model.BioCategory;
import com.example.bio.model.Biography;
import com.example.bio.model.User;
import com.example.bio.security.service.UserDetailsImpl;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.BiographyService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
public class BiographyServiceImpl extends ServiceImpl<BiographyMapper, Biography> implements BiographyService {

    @Autowired
    private BioCategoryService categoryService;

    @Autowired
    private UserService userService;


    @Override
    public void saveBiography(UpdateBiographyDto biographyDto) {
        Biography biography = new Biography();
        biography.setId(biographyDto.getId());
        biography.setOwnerId(biographyDto.getOwnerId());
        biography.setTitle(biographyDto.getTitle());
        biography.setContent(biographyDto.getContent());
        biography.setCategoryId(biographyDto.getCategoryId());

        QueryWrapper<BioCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("id", biographyDto.getCategoryId());
        biography.setCategoryName(categoryService.getOne(wrapper).getCategoryName());

        biography.setEnableComment(biographyDto.getEnableComment());
        biography.setNote(biographyDto.getNote());
        biography.setPenName(biographyDto.getPenName());
        biography.setPrivacyLevel(biographyDto.getPrivacyLevel());
        biography.setStatus(biographyDto.getStatus());
        biography.setTags(biographyDto.getTags());
        save(biography);

    }

    @Override
    public List<Biography> getBiographiesPage(PageQueryParams pageQueryParams) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        Map<String, Object> conditions = pageQueryParams.getConditions();
        Object privacyLevel = conditions.get("privacyLevel");
        Object status = conditions.get("status");
        Object categoryName = conditions.get("categoryName");
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，登陆后查看");
        }
        String id = currentUser.getId();
        wrapper.eq("owner_id", id);
        wrapper.eq("is_deleted", 0);
        if (ObjectUtil.isNotNull(privacyLevel)) {
            wrapper.eq(true, "privacy_level", privacyLevel);
        }
        if (ObjectUtil.isNotNull(status)) {
            wrapper.eq(true, "status", status);
        }
        if (ObjectUtil.isNotNull(categoryName)) {
            wrapper.eq(true, "category_name", categoryName);
        }
        IPage<Biography> biographyPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")));
        return biographyPage.getRecords();
    }

    @Override
    public List<Biography> getOthersBiographies(PageQueryParams pageQueryParams) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        Map<String, Object> conditions = pageQueryParams.getConditions();
        Object categoryName = conditions.get("categoryName");
        Object ownerId = conditions.get("ownerId");
        if (ObjectUtil.isNotNull(ownerId)) {
            wrapper.eq(true, "owner_id", ownerId);
        }
        if (ObjectUtil.isNotNull(categoryName)) {
            wrapper.eq(true, "category_name", categoryName);
        }
        wrapper.eq("privacy_level", 0)
                .eq("status", 1)
                .eq("is_deleted", 0);
        IPage<Biography> biographyPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")));
        return biographyPage.getRecords();
    }

    @Override
    public List<Biography> getPublicBiographyList(PageQueryParams pageQueryParams) {
        QueryWrapper<Biography> queryWrapper = new QueryWrapper<>();
        Map<String, Object> conditions = pageQueryParams.getConditions();
        Object categoryName = conditions.get("categoryName");
        Object ownerId = conditions.get("ownerId");
        if (ObjectUtil.isNotNull(categoryName)) {
            queryWrapper.eq(true, "category_name", categoryName);
        }
        if (ObjectUtil.isNotNull((ownerId))) {
            queryWrapper.eq(true, "owner_id", ownerId);
        }
        queryWrapper.eq("privacy_level", 0)
                .eq("status", 1)
                .eq("is_deleted", 0);
        IPage<Biography> biographyIPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), queryWrapper);
        return biographyIPage.getRecords();
    }

    @Override
    public Biography getBiographyById(String id) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，登录后查看");
        }
        wrapper
                .eq("owner_id", currentUser.getId())
                .eq("id", id)
                .eq("is_deleted", 0);

        return getOne(wrapper);
    }

    @Override
    public Biography getOthersBiographyById(String id) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("is_deleted", 0)
                .eq("privacy_level", 0)
                .eq("status", 1);

        return getOne(wrapper);
    }
}
