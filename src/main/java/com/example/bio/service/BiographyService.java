package com.example.bio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.model.Biography;
import org.springframework.transaction.annotation.Transactional;

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
    IPage<Biography> getBiographiesPage(PageQueryParams pageQueryParams);

    /**
     * 获取他人传记分页
     *
     * @param pageQueryParams
     * @return
     */
    IPage<Biography> getOthersBiographies(PageQueryParams pageQueryParams);

    /**
     * 游客获取公共传记列表
     *
     * @param pageQueryParams
     * @return
     */
    IPage<Biography> getPublicBiographyList(PageQueryParams pageQueryParams);

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

    /**
     * 点赞 / 取消点赞传记（Redis 防重复，操作结果同步到 DB）
     *
     * @param bioId 传记id
     * @return true-点赞成功，false-已取消点赞
     */
    boolean toggleLike(String bioId);

    /**
     * 检查当前用户是否已点赞某传记
     *
     * @param bioId 传记id
     * @return true-已点赞，false-未点赞
     */
    boolean hasLiked(String bioId);

}
