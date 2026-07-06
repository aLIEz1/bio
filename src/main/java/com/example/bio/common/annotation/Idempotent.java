package com.example.bio.common.annotation;

import java.lang.annotation.*;

/**
 * 幂等性注解 — 防止重复提交
 * 基于 Redis 实现：请求携带 idempotent-token 请求头，
 * 服务端校验 token 是否存在且未使用，使用后立即删除。
 *
 * @author bio
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    /**
     * 幂等 token 的有效时间（秒），默认 60 秒
     */
    long expireSeconds() default 60;

    /**
     * 请求头中 token 的 key 名称
     */
    String headerName() default "Idempotent-Token";
}

