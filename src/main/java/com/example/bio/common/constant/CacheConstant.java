package com.example.bio.common.constant;

/**
 * @author zhangfuqi
 * @date 2020/11/17
 */
public interface CacheConstant {

    /**
     * 默认数据库
     */
    String REDIS_DATABASE = "mall";

    /**
     * 过期时间为一天
     */
    Long REDIS_EXPIRE = 86400L;

    /**
     * 用户缓存键
     */
    String REDIS_KEY_USER = "bio:user";

    /**
     * 验证码缓存过期时间90秒
     */
    Long REDIS_EXPIRE_AUTH_CODE = 90L;
    /**
     * 验证码缓存键
     */
    String REDIS_KEY_AUTH_CODE = "bio:authCode";

    /**
     * 类别缓存键
     */
    String REDIS_KEY_CATEGORY = "bio:category";

    /**
     * 重置密码缓存键
     */
    String REDIS_KEY_RESET_PASSWORD = "bio:resetPassword";

    /**
     * 重置密码缓存过期时间30分钟
     */
    Long REDIS_EXPIRE_RESET_PASSWORD = 1800L;
}
