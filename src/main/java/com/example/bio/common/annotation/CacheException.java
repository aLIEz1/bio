package com.example.bio.common.annotation;

import java.lang.annotation.*;

/**
 *  羞羞大牛逼
 *  秀秀龙牛逼
 *  大哥才是真的牛逼
 *  龙哥才是真真正正的牛逼
 * 自定义注解，有该注解的缓存方法会抛出异常
 *
 * @author zhangfuqi
 * @date 2020/11/2
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
