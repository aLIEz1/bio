package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.model.BioCategory;
import com.example.bio.model.Biography;
import com.example.bio.mapper.BiographyMapper;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.BiographyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public IPage<Biography> getPublicBiographyList(PageQueryParams pageQueryParams) {
        QueryWrapper<Biography> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("privacy_level", 0)
                .eq("status", 1)
                .eq("is_deleted", 0);
        return page(pageQueryParams.getPage(), queryWrapper);
    }

    @Override
    public void saveBiography(BiographyDto biographyDto) {
        Biography biography = new Biography();
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
}
