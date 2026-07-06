# ============================================================
# Multi-stage build
# Stage 1: Build with Maven
# Stage 2: Minimal runtime image
# ============================================================

# ---------- Stage 1: Build ----------
FROM maven:3.8.8-eclipse-temurin-8 AS builder

WORKDIR /build

# 先只复制 pom.xml，利用 Docker layer 缓存依赖
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 再复制源码编译
COPY src ./src
RUN mvn package -DskipTests -B

# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:8-jre-alpine

LABEL maintainer="zhangfuqi <1742720898@qq.com>"
LABEL description="Biography Platform Backend"

# 时区设置
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

WORKDIR /app

# 从 builder 阶段复制 jar
COPY --from=builder /build/target/bio-0.0.1-SNAPSHOT.jar app.jar

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
    CMD wget -qO- http://localhost:8082/actuator/health || exit 1

EXPOSE 8082

ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}", \
    "-jar", "app.jar"]
