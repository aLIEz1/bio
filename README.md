# 博客项目后端API接口
[![Build Status](https://travis-ci.com/aLIEz1/bio.svg?token=QfoSAd13NxgfgoqsozMM&branch=master)](https://travis-ci.com/aLIEz1/bio)

> 主要使用的技术

|          技术          |  版本   |       说明       |
| :--------------------: | :-----: | :--------------: |
|      Spring Boot       |  2.3.4  |   容器+MVC框架   |
|    Spring Security     |  2.3.4  |  认证和授权框架  |
|      Mybatis Plus      |  3.4.0  |     ORM框架      |
|          JWT           |  0.9.1  |   JWT登陆支持    |
| Mybatis Plus Generator |  3.4.0  |  数据层代码生成  |
|       Swagger-UI       |  3.0.0  |   文档生产工具   |
|         Redis          |   5.0   |    分布式缓存    |
|         Druid          |  1.2.1  |   数据库连接池   |
|         Lombok         | 1.18.12 | 简化对象封装工具 |
|        RabbitMQ        |  3.8.9  |     消息队列     |
|     Elasticsearch      | 7.10.0  |     搜索引擎     |

## 文件目录

```
├─bio.log # 日志目录
│  ├─debug
│  └─error
├─db # 数据库目录
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─example
│  │  │          └─bio
│  │  │              ├─common # 公用类
│  │  │              │  ├─annotation # 通用注解
│  │  │              │  ├─api # 对结果集封装
│  │  │              │  ├─aspect # Redis缓存切面
│  │  │              │  ├─component # RabbitMQ sender receiver
│  │  │              │  ├─config # 通用配置
│  │  │              │  ├─constant # 常量类
│  │  │              │  ├─constraint # 约束
│  │  │              │  ├─domain # 公用属性
│  │  │              │  ├─interceptor # 限流拦截器
│  │  │              │  ├─limiter # Redis限流实现
│  │  │              │  └─lock # Redission分布式锁实现限流
│  │  │              ├─config # 配置类
│  │  │              ├─controller # 前端控制器
│  │  │              ├─dto # 数据传输对象
│  │  │              ├─exception # 异常处理
│  │  │              ├─log # 日志处理
│  │  │              ├─mapper # mybatis-plus mapper接口
│  │  │              │  └─elasticsearch # elasticsearch repo
│  │  │              ├─model # 实体类
│  │  │              │  └─elasticsearch # elasticsearch 实体类
│  │  │              ├─security # 安全配置
│  │  │              │  ├─component # 权限过滤等组件
│  │  │              │  ├─config # 配置类
│  │  │              │  └─service # 安全服务类
│  │  │              ├─service # 服务
│  │  │              │  └─impl # 服务实现
│  │  │              ├─util # 工具类
│  │  │              └─vo # 数据展示对象
│  │  └─resources # 资源目录
│  │      ├─mapper # mapper.xml目录
│  │      ├─static # 静态资源
│  │      └─templates # thymeleaf 网页模板
│  └─test # 测试
│      └─java
│          └─com
│              └─example
│                  └─bio

```



## 搭建步骤

### IDEA

- 关于IDEA的安装与使用请参考：https://github.com/judasn/IntelliJ-IDEA-Tutorial
- 搜索插件仓库，安装插件`Lombok`；
- ![](https://cdn.nlark.com/yuque/0/2020/png/1792508/1604294500238-f1f8a165-6116-4c87-89bc-43a41092552d.png)
- 将项目解压到本地，然后直接打开。
- ![](https://cdn.nlark.com/yuque/0/2020/png/1792508/1604294631599-6a96a0aa-3121-4f6f-a07f-cffcec67c899.png)

### 安装mysql

- 下载并安装mysql`8.0.21`版本，下载地址：https://dev.mysql.com/downloads/installer/
- 修改数据库帐号密码为你的用户名与密码，**该文件位于src/main/resources目录下的application.yml**
- ![](https://cdn.nlark.com/yuque/0/2020/png/1792508/1604293694149-3cc4cd2f-cf7f-4da8-a8da-efc47ae9b307.png)
- 下载并安装客户端连接工具Navicat,下载地址：http://www.formysql.com/xiazai.html
- 创建数据库`biography`
- 导入db下的`biography.sql`文件

### 安装Redis

- 下载Redis,下载地址：https://github.com/MicrosoftArchive/redis/releases
- 下载完后解压到指定目录；
- 在当前地址栏输入cmd后，执行redis的启动命令：`redis-server.exe redis.windows.conf`

### 安装RabbitMQ

- 安装Erlang，下载地址：http://erlang.org/download/otp_win64_21.3.exe
- 安装RabbitMQ，下载地址：https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.14/rabbitmq-server-3.7.14.exe

### 安装Elasticsearch

- 下载Elasticsearch，下载地址：https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.10.0-windows-x86_64.zip 

- 下载完解压到指定目录

- 打开bin目录，在当前地址栏输入cmd后，输入命令 `elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.10.0/elasticsearch-analysis-ik-7.10.0.zip`

- 提示安装完成后输入命令`elasticsearch`

  

### 启动项目

- 启动项目：直接运行com.example.bio.BioApplication的main方法即可；
- 接口文档地址：http://localhost:8082/swagger-ui/
