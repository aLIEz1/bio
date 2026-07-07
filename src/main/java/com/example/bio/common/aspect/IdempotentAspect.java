package com.example.bio.common.aspect;

import com.example.bio.common.annotation.Idempotent;
import com.example.bio.common.api.ResultUtil;
import com.example.bio.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 幂等性切面 — 防止重复提交
 * 基于 Redis 实现请求幂等性：
 * 1. 前端先调用获取 token 的接口拿到唯一 token
 * 2. 提交时在请求头中携带该 token
 * 3. 切面校验 token 是否存在且未使用，使用后立即删除
 *
 * @author bio
 */
@Aspect
@Component
@Order(1)
public class IdempotentAspect {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IdempotentAspect.class);


    private final RedisService redisService;

    private static final String IDEMPOTENT_PREFIX = "idempotent:";

    public IdempotentAspect(RedisService redisService) {
        this.redisService = redisService;
    }

    @Around("@annotation(com.example.bio.common.annotation.Idempotent)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 从请求头获取幂等 token
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        Idempotent idempotent = null;

        // 获取方法上的 Idempotent 注解
        try {
            org.aspectj.lang.reflect.MethodSignature signature = (org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature();
            idempotent = signature.getMethod().getAnnotation(Idempotent.class);
        } catch (Exception e) {
            log.warn("获取Idempotent注解失败", e);
        }

        String headerName = idempotent != null ? idempotent.headerName() : "Idempotent-Token";
        String token = request.getHeader(headerName);

        if (token == null || token.isEmpty()) {
            // 没有 token，不允许重复提交的接口必须携带 token
            return ResultUtil.buildError("缺少幂等性校验 token，请刷新页面重试");
        }

        String redisKey = IDEMPOTENT_PREFIX + token;

        // 尝试删除 token（原子操作：存在则删除返回 true，不存在返回 false）
        Boolean deleted = redisService.del(redisKey);
        if (deleted == null || !deleted) {
            // token 不存在或已使用，说明是重复请求
            log.warn("幂等性校验失败，重复请求：token={}", token);
            return ResultUtil.buildError("请勿重复提交");
        }

        // token 校验通过，执行业务逻辑
        return joinPoint.proceed();
    }

    /**
     * 生成幂等性 token 并存入 Redis
     *
     * @param redisService  Redis 服务
     * @param expireSeconds token 有效时间（秒）
     * @return 生成的 token 值
     */
    public static String generateToken(RedisService redisService, long expireSeconds) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String redisKey = IDEMPOTENT_PREFIX + token;
        redisService.set(redisKey, "1", expireSeconds);
        return token;
    }
}

