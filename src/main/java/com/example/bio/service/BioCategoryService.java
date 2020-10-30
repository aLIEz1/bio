package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.model.BioCategory;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BioCategoryService extends IService<BioCategory> {
    /**
     * 获取传记类别分页
     *
     * @param pageQueryParams
     * @return
     */
    List<BioCategory> getAllCategoriesPage(PageQueryParams pageQueryParams);

}
