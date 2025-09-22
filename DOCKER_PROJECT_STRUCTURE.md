# 记账管理系统 Docker 项目结构

## 📁 项目目录结构

```
basicJp/
├── 📁 backend/                    # 后端服务 (Spring Boot)
│   ├── 📄 Dockerfile             # 后端Docker配置
│   ├── 📄 .dockerignore          # Docker忽略文件
│   ├── 📄 pom.xml                # Maven配置
│   └── 📁 src/                   # 源代码
├── 📁 frontend/                   # 前端服务 (Next.js)
│   ├── 📄 Dockerfile             # 前端Docker配置
│   ├── 📄 .dockerignore          # Docker忽略文件
│   ├── 📄 package.json           # Node.js配置
│   └── 📁 src/                   # 源代码
├── 📁 data/                      # 数据存储目录
├── 📄 docker-compose.yml         # Docker Compose配置
├── 📄 docker-start.sh            # 启动脚本
├── 📄 docker-stop.sh             # 停止脚本
├── 📄 test-docker.sh             # 测试脚本
├── 📄 DOCKER_README.md           # Docker使用说明
└── 📄 DOCKER_PROJECT_STRUCTURE.md # 本文档
```

## 🐳 Docker 配置说明

### 后端服务 (accounting-backend)
- **基础镜像**: `maven:3.9.6-openjdk-17-slim` (构建) + `openjdk:17-jre-slim` (运行)
- **端口**: 8080
- **数据存储**: H2数据库文件存储
- **健康检查**: `/actuator/health`
- **用户**: spring (非root用户)

### 前端服务 (accounting-frontend)
- **基础镜像**: `node:18-alpine`
- **端口**: 3000
- **环境**: production
- **健康检查**: HTTP GET /
- **用户**: nextjs (非root用户)

### 网络配置
- **网络名称**: `accounting-network`
- **网络类型**: bridge
- **服务间通信**: 通过容器名称

### 数据持久化
- **数据目录**: `./data`
- **数据库文件**: `./data/accounting.mv.db`
- **挂载方式**: bind mount

## 🚀 快速启动命令

```bash
# 1. 一键启动
./docker-start.sh

# 2. 手动启动
docker-compose up -d --build

# 3. 查看状态
docker-compose ps

# 4. 查看日志
docker-compose logs -f

# 5. 停止服务
./docker-stop.sh
```

## 🔧 开发调试

### 进入容器
```bash
# 进入后端容器
docker-compose exec backend bash

# 进入前端容器
docker-compose exec frontend sh
```

### 重新构建
```bash
# 重新构建所有服务
docker-compose build --no-cache

# 重新构建特定服务
docker-compose build backend
docker-compose build frontend
```

### 查看资源使用
```bash
# 查看容器资源使用
docker stats

# 查看特定容器
docker stats accounting-backend accounting-frontend
```

## 📊 服务监控

### 健康检查端点
- **后端**: http://localhost:8080/actuator/health
- **前端**: http://localhost:3000

### 日志查看
```bash
# 所有服务日志
docker-compose logs -f

# 特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend

# 带时间戳的日志
docker-compose logs -f -t
```

## 🛠️ 故障排除

### 常见问题及解决方案

1. **端口冲突**
   - 检查端口占用: `lsof -i :3000` 或 `lsof -i :8080`
   - 修改端口: 编辑 `docker-compose.yml` 中的 ports 配置

2. **服务启动失败**
   - 查看日志: `docker-compose logs [service-name]`
   - 检查配置: `docker-compose config`
   - 重新构建: `docker-compose build --no-cache`

3. **数据丢失**
   - 检查数据目录权限: `ls -la ./data`
   - 重新创建数据目录: `mkdir -p ./data && chmod 755 ./data`

4. **网络问题**
   - 检查网络: `docker network ls`
   - 重启网络: `docker-compose down && docker-compose up -d`

## 🔄 更新和维护

### 更新代码
```bash
# 1. 停止服务
docker-compose down

# 2. 拉取最新代码
git pull

# 3. 重新构建并启动
docker-compose up -d --build
```

### 备份数据
```bash
# 备份数据目录
cp -r ./data ./data-backup-$(date +%Y%m%d)

# 恢复数据
cp -r ./data-backup-20250922 ./data
```

### 完全清理
```bash
# 停止并删除所有资源
docker-compose down --rmi all --volumes

# 清理Docker系统
docker system prune -a
```

## 📝 环境变量说明

### 后端环境变量
- `SPRING_PROFILES_ACTIVE=docker` - Spring配置文件
- `SERVER_PORT=8080` - 服务端口
- `SPRING_DATASOURCE_URL=jdbc:h2:file:/app/data/accounting` - 数据库URL
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver` - 数据库驱动
- `SPRING_DATASOURCE_USERNAME=sa` - 数据库用户名
- `SPRING_DATASOURCE_PASSWORD=` - 数据库密码
- `SPRING_H2_CONSOLE_ENABLED=true` - 启用H2控制台

### 前端环境变量
- `NODE_ENV=production` - Node.js环境
- `NEXT_PUBLIC_API_URL=http://localhost:8080` - API地址

## 🎯 最佳实践

1. **定期备份数据**
2. **监控资源使用情况**
3. **及时更新基础镜像**
4. **使用非root用户运行服务**
5. **配置适当的健康检查**
6. **设置服务重启策略**

---

**项目已完全Docker化，可以轻松部署和运行！** 🎉
