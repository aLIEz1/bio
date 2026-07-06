# 一键 Docker 部署

## 环境要求

- Docker >= 20.10
- Docker Compose >= 2.0（或 `docker compose` 插件）

## 快速启动

```bash
# 1. 克隆项目
git clone https://github.com/aLIEz1/bio.git && cd bio

# 2. 复制环境变量配置并修改
cp .env.example .env
# 编辑 .env，至少修改：
#   MYSQL_ROOT_PASSWORD、JWT_SECRET、邮件相关配置

# 3. 一键启动（首次构建约需 3-5 分钟）
docker compose up -d

# 4. 查看启动状态
docker compose ps
docker compose logs -f bio-app
```

## 服务端口

| 服务 | 端口 | 说明 |
|---|---|---|
| bio 后端 | 8082 | API 根路径 `/api` |
| MySQL | 3306 | 数据库 |
| Redis | 6379 | 缓存 |
| RabbitMQ | 5672 / 15672 | 消息队列 / 管理界面 |
| Elasticsearch | 9200 | 全文搜索 |
| MinIO | 9000 / 9001 | 对象存储 / 控制台 |

## 常用命令

```bash
# 停止所有服务
docker compose down

# 停止并删除数据卷（⚠️ 会清空所有数据）
docker compose down -v

# 重新构建 bio-app 镜像（代码有更新时）
docker compose build bio-app
docker compose up -d bio-app

# 查看日志
docker compose logs -f bio-app
docker compose logs -f mysql

# 进入容器
docker compose exec bio-app sh
docker compose exec mysql mysql -uroot -p
```

## 访问地址

- **Swagger API 文档**：http://localhost:8082/swagger-ui/index.html
- **Druid 监控**：http://localhost:8082/druid/index.html （admin / 123456）
- **RabbitMQ 管理**：http://localhost:15672 （guest / guest）
- **MinIO 控制台**：http://localhost:9001 （minioadmin / minioadmin）

## 数据持久化

所有数据均通过 Docker Volume 持久化：

```
mysql_data    → MySQL 数据库文件
redis_data    → Redis AOF 持久化文件
rabbitmq_data → RabbitMQ 消息数据
es_data       → Elasticsearch 索引数据
minio_data    → 上传的文件/图片
app_logs      → 后端日志 (/app/logs/bio.log)
```
