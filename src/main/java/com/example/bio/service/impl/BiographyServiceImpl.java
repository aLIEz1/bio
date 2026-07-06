package com.example.bio.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.BiographyMapper;
import com.example.bio.model.BioCategory;
import com.example.bio.model.BioTag;
import com.example.bio.model.Biography;
import com.example.bio.model.User;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.BioTagService;
import com.example.bio.service.BiographyService;
import com.example.bio.service.RedisService;
import com.example.bio.service.UserActiveService;
import com.example.bio.service.UserService;
import org.springframework.beans.BeanUtils;
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
public class BiographyServiceImpl extends ServiceImpl<BiographyMapper, Biography> implements BiographyService {

    private static final String LIKE_KEY_PREFIX = "bio:like:";

    @Autowired
    private BioCategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BioTagService tagService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserActiveService userActiveService;


    @Override
    public void saveBiography(BiographyDto biographyDto) {
        Biography biography = new Biography();
        BeanUtils.copyProperties(biographyDto, biography);
        biography.setOwnerId(userService.getCurrentUser().getId());
        BioCategory category = categoryService.getById(biographyDto.getCategoryId());
        if (category == null) {
            Asserts.fail("分类不存在");
        }
        biography.setCategoryName(category.getCategoryName());
        Set<BioTag> tags = biographyDto.getTags();
        save(biography);
        tagService.addBiographyTags(biography.getId(), tags);
        // 更新用户活跃统计
        userActiveService.incrementBioNum(biography.getOwnerId());
    }

    @Override
    public void updateBiography(UpdateBiographyDto biographyDto) {
        Biography biography = new Biography();
        QueryWrapper<Biography> biographyQueryWrapper = new QueryWrapper<>();
        biographyQueryWrapper
                .eq("is_deleted", 0)
                .eq("id", biographyDto.getId());
        Biography one = getOne(biographyQueryWrapper);
        if (one == null) {
            Asserts.fail("传记不存在");
        }
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
    public IPage<Biography> getBiographiesPage(PageQueryParams pageQueryParams) {
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
        fillTagsBatch(biographyPage.getRecords());
        return biographyPage;
    }

    @Override
    public IPage<Biography> getOthersBiographies(PageQueryParams pageQueryParams) {
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
        fillTagsBatch(biographyPage.getRecords());
        return biographyPage;
    }

    @Override
    public IPage<Biography> getPublicBiographyList(PageQueryParams pageQueryParams) {
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
        fillTagsBatch(biographyPage.getRecords());
        return biographyPage;
    }

    @Override
    public Biography getBiographyById(String id) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，登录后查看");
        }
        wrapper.eq("owner_id", currentUser.getId())
                .eq("id", id)
                .eq("is_deleted", 0);
        Biography biography = getOne(wrapper);
        if (biography == null) {
            Asserts.fail("传记不存在");
        }
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
        if (biography == null) {
            Asserts.fail("传记不存在或无权访问");
        }
        biography.setTags(tagService.getTagsByBiographyId(biography.getId()));
        return biography;
    }

    @Override
    public boolean toggleLike(String bioId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        String likeKey = LIKE_KEY_PREFIX + bioId;
        String userId = currentUser.getId();

        boolean alreadyLiked = Boolean.TRUE.equals(redisService.sIsMember(likeKey, userId));
        UpdateWrapper<Biography> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bioId).eq("is_deleted", 0);

        if (alreadyLiked) {
            // 取消点赞
            redisService.sRemove(likeKey, userId);
            updateWrapper.setSql("likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END");
            update(updateWrapper);
            return false;
        } else {
            // 点赞
            redisService.sAdd(likeKey, userId);
            updateWrapper.setSql("likes = likes + 1");
            update(updateWrapper);
            return true;
        }
    }

    /**
     * 批量填充传记标签，解决 N+1 查询问题
     */
    private void fillTagsBatch(List<Biography> records) {
        if (records == null || records.isEmpty()) {
            return;
        }
        List<String> bioIds = records.stream()
                .map(Biography::getId)
                .collect(Collectors.toList());
        Map<String, Set<BioTag>> tagsMap = tagService.getTagsByBiographyIds(bioIds);
        for (Biography record : records) {
            record.setTags(tagsMap.getOrDefault(record.getId(), Collections.emptySet()));
        }
    }

    @Override
    public boolean hasLiked(String bioId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        String likeKey = LIKE_KEY_PREFIX + bioId;
        return Boolean.TRUE.equals(redisService.sIsMember(likeKey, currentUser.getId()));
    }
}
