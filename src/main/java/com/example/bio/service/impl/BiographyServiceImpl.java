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
import com.example.bio.model.BioTag;
import com.example.bio.model.Biography;
import com.example.bio.model.User;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.BioTagService;
import com.example.bio.service.BiographyService;
import com.example.bio.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
public class BiographyServiceImpl extends ServiceImpl<BiographyMapper, Biography> implements BiographyService {

    @Autowired
    private BioCategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BioTagService tagService;


    @Override
    public void saveBiography(BiographyDto biographyDto) {
        Biography biography = new Biography();
        BeanUtils.copyProperties(biographyDto, biography);
        biography.setOwnerId(userService.getCurrentUser().getId());
        biography.setCategoryName(categoryService.getById(biographyDto.getCategoryId()).getCategoryName());
        Set<BioTag> tags = biographyDto.getTags();
        tags.forEach(System.out::println);
        save(biography);
        tagService.addBiographyTags(biography.getId(), tags);
    }

    @Override
    public void updateBiography(UpdateBiographyDto biographyDto) {
        Biography biography = new Biography();
        QueryWrapper<Biography> biographyQueryWrapper = new QueryWrapper<>();
        biographyQueryWrapper
                .eq("is_deleted", 0)
                .eq("id", biographyDto.getId());
        Biography one = getOne(biographyQueryWrapper);
        if (!one.getOwnerId().equals(userService.getCurrentUser().getId())) {
            Asserts.fail("没有权限访问");
        } else {
            BeanUtils.copyProperties(one, biographyDto);
            BeanUtils.copyProperties(biographyDto, biography);
            biography.setOwnerId(userService.getCurrentUser().getId());
            saveOrUpdate(biography);

        }

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
        IPage<Biography> biographyPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), wrapper);
        List<Biography> records = biographyPage.getRecords();
        for (Biography record : records) {
            record.setTags(tagService.getTagsByBiographyId(record.getId()));
        }
        return records;
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
        IPage<Biography> biographyPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), wrapper);
        List<Biography> records = biographyPage.getRecords();
        for (Biography record : records) {
            record.setTags(tagService.getTagsByBiographyId(record.getId()));
        }
        return records;
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
        IPage<Biography> biographyPage = page(pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")), queryWrapper);
        List<Biography> records = biographyPage.getRecords();
        for (Biography record : records) {
            record.setTags(tagService.getTagsByBiographyId(record.getId()));
        }
        return records;
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

        Biography biography = getOne(wrapper);
        biography.setTags(tagService.getTagsByBiographyId(biography.getId()));
        return biography;
    }

    @Override
    public Biography getOthersBiographyById(String id) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("is_deleted", 0)
                .eq("privacy_level", 0)
                .eq("status", 1);

        Biography biography = getOne(wrapper);
        biography.setTags(tagService.getTagsByBiographyId(biography.getId()));
        return biography;
    }
}
