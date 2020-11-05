package com.example.bio.service;

import com.example.bio.model.BioCategory;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
public interface BioCategoryCacheService {
    /**
     * 删除缓存
     *
     * @param id
     */
    void deleteCategory(String id);

    /**
     * get
     *
     * @param id
     * @return
     */
    BioCategory getCategory(String id);

    /**
     * set
     *
     * @param bioCategory
     */
    void setCategory(BioCategory bioCategory);
}
