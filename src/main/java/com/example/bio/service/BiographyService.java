package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.model.Biography;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    void saveBiography(BiographyDto biographyDto);

    /**
     * 更新传记
     *
     * @param biographyDto
     */
    void updateBiography(UpdateBiographyDto biographyDto);

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

    /**
     * 根据id获取私人传记
     *
     * @param id
     * @return
     */
    Biography getBiographyById(String id);

    /**
     * 获取他人传记
     *
     * @param id
     * @return
     */
    Biography getOthersBiographyById(String id);

}
