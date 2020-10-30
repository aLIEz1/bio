package com.example.bio.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     *
     * @return
     */
    Map<String, ConfigAttribute> loadDataSource();
}
