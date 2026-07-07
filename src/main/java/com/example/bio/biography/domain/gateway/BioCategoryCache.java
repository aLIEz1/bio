package com.example.bio.biography.domain.gateway;

import com.example.bio.biography.domain.model.BioCategory;

/**
 * 分类缓存端口（接口），由 infrastructure/cache 实现。
 */
public interface BioCategoryCache {
    BioCategory get(String id);
    void put(BioCategory category);
    void evict(String id);
}
