# 多阶段构建 - 构建阶段
FROM maven:3.9-openjdk-17-slim AS build

WORKDIR /app

# 复制Maven配置文件
COPY pom.xml .

# 下载依赖（利用Docker缓存层）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:17-slim

WORKDIR /app

# 创建数据目录
RUN mkdir -p /app/data

# 从构建阶段复制jar文件
COPY --from=build /app/target/accounting-system-1.0.0.jar app.jar

# 创建非root用户
RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app
USER spring:spring

# 暴露端口
EXPOSE 8080

# 安装curl用于健康检查
USER root
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
USER spring:spring

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 运行应用
ENTRYPOINT ["java", "-jar", "app.jar"]
