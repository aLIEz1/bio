package com.example.bio.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.example.bio.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 *
 * @author zhangfuqi
 * @date 2020/10/27
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebLogAspect.class);


    @Pointcut("execution(public * com.example.bio.controller.*.*(..))&&@annotation(io.swagger.annotations.ApiOperation))")

    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinpoint) throws Throwable {

    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturn(Object ret) throws Throwable {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            WebLog webLog = new WebLog();
            Object result = joinPoint.proceed();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (method.isAnnotationPresent(ApiOperation.class)) {
                ApiOperation log = method.getAnnotation(ApiOperation.class);
                webLog.setDescription(log.value());
            }
            long endTime = System.currentTimeMillis();
            String urlStr = request.getRequestURL().toString();
            webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
            webLog.setIp(RequestUtil.getRequestIp(request));
            webLog.setMethod(request.getMethod());
            webLog.setParameter(getParameter(method, joinPoint.getArgs()));
            webLog.setResult(result);
            webLog.setSpendTime((int) (endTime - startTime));
            webLog.setStartTime(startTime);
            webLog.setUri(request.getRequestURI());
            webLog.setUrl(request.getRequestURL().toString());
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("ip", webLog.getIp());
            logMap.put("url", webLog.getUrl());
            logMap.put("method", webLog.getMethod());
            logMap.put("parameter", webLog.getParameter());
            logMap.put("spendTime", webLog.getSpendTime());
            logMap.put("description", webLog.getDescription());
            log.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
            return result;

        } else {
            return null;
        }
    }

    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(desensitize(args[i]));
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, desensitize(args[i]));
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    /**
     * 对敏感字段进行脱敏处理
     */
    private Object desensitize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<Object, Object> result = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (isSensitiveKey(String.valueOf(entry.getKey()))) {
                    result.put(entry.getKey(), "******");
                } else {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
            return result;
        }
        // 对于自定义对象，通过 JSON 序列化后脱敏
        try {
            String json = JSONUtil.parse(obj).toString();
            Map<String, Object> map = JSONUtil.toBean(json, Map.class);
            if (map != null) {
                Map<String, Object> result = new HashMap<>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (isSensitiveKey(entry.getKey())) {
                        result.put(entry.getKey(), "******");
                    } else {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
                return result;
            }
        } catch (Exception ignored) {
            // 脱敏失败则返回原始值
        }
        return obj;
    }

    private boolean isSensitiveKey(String key) {
        if (key == null) {
            return false;
        }
        String lowerKey = key.toLowerCase();
        return lowerKey.contains("password") || lowerKey.contains("pwd")
                || lowerKey.contains("authcode") || lowerKey.contains("auth_code")
                || lowerKey.contains("token") || lowerKey.contains("secret")
                || lowerKey.contains("credential");
    }
}
