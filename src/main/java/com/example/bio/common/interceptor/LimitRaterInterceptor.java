package com.example.bio.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.bio.common.annotation.RateLimiter;
import com.example.bio.common.constant.CommonConstant;
import com.example.bio.common.domain.IpLimitProperties;
import com.example.bio.common.domain.LimitProperties;
import com.example.bio.common.limiter.RedisRaterLimiter;
import com.example.bio.exception.LimitException;
import com.example.bio.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Component
@Slf4j
public class LimitRaterInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private IpLimitProperties ipLimitProperties;

    @Autowired
    private LimitProperties limitProperties;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = RequestUtil.getRequestIp(request);
        if (ipLimitProperties.getEnable()) {
            Boolean token1 = redisRaterLimiter.acquireByRedis(ip,
                    ipLimitProperties.getLimit(), ipLimitProperties.getTimeout());
            if (!token1) {
                throw new LimitException("你手速怎么这么快，请点慢一点");
            }
        }
        if (limitProperties.getEnable()) {
            Boolean token2 = redisRaterLimiter.acquireByRedis(CommonConstant.LIMIT_ALL,
                    limitProperties.getLimit(), limitProperties.getTimeout());
            if (!token2) {
                throw new LimitException("当前访问总人数太多啦，请稍后再试");
            }
        }
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            Method method = handlerMethod.getMethod();
            RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
            if (rateLimiter != null) {
                String name = rateLimiter.name();
                Long limit = rateLimiter.rate();
                Long timeout = rateLimiter.rateInterval();
                if (StrUtil.isBlank(name)) {
                    name = StrUtil.subBefore(bean.toString(), "@", false) + "_" + method.getName();
                }
                if (rateLimiter.ipLimit()) {
                    name += "_" + ip;
                }
                Boolean token3 = redisRaterLimiter.acquireByRedis(name, limit, timeout);
                if (!token3) {
                    throw new LimitException("当前访问总人数太多啦，请稍后再试");
                }
            }
        } catch (LimitException e) {
            throw new LimitException(e.getMsg());
        } catch (Exception e) {
        }

        return true;
    }
}
