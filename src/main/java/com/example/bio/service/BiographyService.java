package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.model.Biography;

import java.util.List;

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
     * 新增传记
     *
     * @param biographyDto
     */
    void saveBiography(BiographyDto biographyDto);

    /**
     * 获取私人传记分页
     *
     * @param pageQueryParams
     * @return
     */
    List<Biography> getBiographiesPage(PageQueryParams pageQueryParams);

    /**
     * 获取他人传记分页
     *
     * @param pageQueryParams
     * @return
     */
    List<Biography> getOthersBiographies(PageQueryParams pageQueryParams);

    /**
     * 游客获取公共传记列表
     *
     * @param pageQueryParams
     * @return
     */
    List<Biography> getPublicBiographyList(PageQueryParams pageQueryParams);

}
