package com.example.bio.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @author super
 */
public class CodeGenerator {
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        GlobalConfig config = new GlobalConfig();
        String path = System.getProperty("user.dir");
        config.setOutputDir(path + "/src/main/java");
        config.setAuthor("zhangfuqi");
        config.setOpen(false);
        config.setFileOverride(true);
        config.setServiceName("%sService");
        config.setIdType(IdType.ASSIGN_ID);
        config.setDateType(DateType.ONLY_DATE);
        config.setSwagger2(true);
        generator.setGlobalConfig(config);


        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/biography?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("74521");
        dataSourceConfig.setDbType(DbType.MYSQL);
        generator.setDataSource(dataSourceConfig);


        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName("bio");
        packageConfig.setParent("com.example");
        packageConfig.setEntity("model");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setController("controller");
        generator.setPackageInfo(packageConfig);


        StrategyConfig strategyConfig = new StrategyConfig();

        /*
        包含的表，可变长参数
         */
        strategyConfig.setInclude("user", "role", "user_info", "bio_category", "bio_comment", "bio_tag", "biography", "user_active", "user_active_token");

        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setLogicDeleteFieldName("is_deleted");
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setEntitySerialVersionUID(true);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        generator.setStrategy(strategyConfig);

        generator.execute();

    }
}