package com.example.bio.config;

import com.example.bio.common.config.BaseSwaggerConfig;
import com.example.bio.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.example.bio.controller")
                .title("自传项目")
                .description("自传项目后端接口文档")
                .contactName("张富起")
                .contactEmail("1742720898@qq.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
