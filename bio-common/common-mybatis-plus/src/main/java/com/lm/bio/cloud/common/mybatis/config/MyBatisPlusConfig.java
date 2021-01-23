package com.lm.bio.cloud.common.mybatis.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lm.bio.cloud.common.mybatis.handler.MetaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @copyright (C), Since 2021
 * @author: lm
 * @date: 2021/1/23 20:14
 * @description: MybatisPlus 配置类
 */
@Configuration
@EnableTransactionManagement
public class MyBatisPlusConfig {
    /**
     * 分页插件
     *
     * @return PaginationInnerInterceptor
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * 自动填充插件
     *
     * @return GlobalConfig
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaHandler());
        return globalConfig;
    }
}
