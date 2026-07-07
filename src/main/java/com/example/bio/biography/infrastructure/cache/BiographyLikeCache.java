package com.example.bio.biography.infrastructure.cache;

import com.example.bio.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * 点赞状态 Redis 缓存封装。
 * key = bio:like:{bioId}，value = Set<userId>
 */
@Component
public class BiographyLikeCache {

    private static final String KEY_PREFIX = "bio:like:";

    private final RedisService redisService;

    public BiographyLikeCache(RedisService redisService) {
        this.redisService = redisService;
    }

    public boolean isLiked(String bioId, String userId) {
        return Boolean.TRUE.equals(redisService.sIsMember(KEY_PREFIX + bioId, userId));
    }

    public void like(String bioId, String userId) {
        redisService.sAdd(KEY_PREFIX + bioId, userId);
    }

    public void unlike(String bioId, String userId) {
        redisService.sRemove(KEY_PREFIX + bioId, userId);
    }
}
