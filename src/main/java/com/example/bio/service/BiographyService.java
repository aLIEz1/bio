package com.example.bio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.model.Biography;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BiographyService extends IService<Biography> {
    /**
     * 获取公开传记列表
     *
     * @param pageQueryParams
     * @return
     */
    IPage<Biography> getPublicBiographyList(PageQueryParams pageQueryParams);

    /**
     * 新增传记
     *
     * @param biographyDto
     */
    void saveBiography(BiographyDto biographyDto);

}
