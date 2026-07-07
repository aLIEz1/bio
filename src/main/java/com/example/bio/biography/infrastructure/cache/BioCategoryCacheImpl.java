package com.example.bio.biography.infrastructure.cache;

import com.example.bio.biography.domain.gateway.BioCategoryCache;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.common.constant.CacheConstant;
import com.example.bio.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * 分类 Redis 缓存实现，实现 application 层 BioCategoryCache 接口。
 */
@Component
public class BioCategoryCacheImpl implements BioCategoryCache {

    private final RedisService redisService;

    public BioCategoryCacheImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    private String key(String id) {
        return CacheConstant.REDIS_DATABASE + ":" + CacheConstant.REDIS_KEY_CATEGORY + ":" + id;
    }

    @Override
    public BioCategory get(String id) {
        Object obj = redisService.get(key(id));
        if (obj instanceof BioCategory) return (BioCategory) obj;
        return null;
    }

    @Override
    public void put(BioCategory category) {
        redisService.set(key(category.getId()), category, CacheConstant.REDIS_EXPIRE);
    }

    @Override
    public void evict(String id) {
        redisService.del(key(id));
    }
}
