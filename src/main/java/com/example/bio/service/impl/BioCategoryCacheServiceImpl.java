package com.example.bio.service.impl;

import com.example.bio.model.BioCategory;
import com.example.bio.service.BioCategoryCacheService;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Service
public class BioCategoryCacheServiceImpl implements BioCategoryCacheService {

    @Autowired
    private BioCategoryService bioCategoryService;

    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${redis.key.category}")
    private String REDIS_KEY_CATEGORY;

    @Override
    public void deleteCategory(String id) {
        if (bioCategoryService.getById(id) != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_CATEGORY + ":" + id;
            redisService.del(key);
        }
    }

    @Override
    public BioCategory getCategory(String id) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_CATEGORY + ":" + id;
        return (BioCategory) redisService.get(key);
    }

    @Override
    public void setCategory(BioCategory bioCategory) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_CATEGORY + ":" + bioCategory.getId();
        redisService.set(key, bioCategory, REDIS_EXPIRE);
    }
}
