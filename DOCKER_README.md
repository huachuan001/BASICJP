# 记账管理系统 Docker 部署指南

## 📋 概述

本项目已完全Docker化，可以通过Docker Desktop和Docker Compose轻松部署和运行。

## 🏗️ 项目架构

```
记账管理系统
├── 前端 (Next.js)     - 端口 3000
├── 后端 (Spring Boot) - 端口 8080
└── 数据存储 (H2)      - 文件存储
```

## 🚀 快速开始

### 前置要求

1. **Docker Desktop** - 请确保已安装并运行
2. **Docker Compose** - 通常随Docker Desktop一起安装

### 一键启动

```bash
# 给脚本执行权限
chmod +x docker-start.sh docker-stop.sh

# 启动系统
./docker-start.sh
```

### 手动启动

```bash
# 1. 创建数据目录
mkdir -p ./data

# 2. 构建并启动服务
docker-compose up -d --build

# 3. 查看服务状态
docker-compose ps
```

## 🌐 访问地址

- **前端应用**: http://localhost:3000
- **后端API**: http://localhost:8080
- **健康检查**: http://localhost:8080/actuator/health
- **H2数据库控制台**: http://localhost:8080/h2-console

## 📝 常用命令

### 服务管理

```bash
# 启动服务
docker-compose up -d

# 停止服务
docker-compose down

# 重启服务
docker-compose restart

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend
```

### 开发调试

```bash
# 进入容器
docker-compose exec backend bash
docker-compose exec frontend sh

# 重新构建特定服务
docker-compose build backend
docker-compose build frontend

# 强制重新构建
docker-compose build --no-cache
```

### 数据管理

```bash
# 备份数据
cp -r ./data ./data-backup-$(date +%Y%m%d)

# 清理数据（谨慎使用）
rm -rf ./data
```

## 🔧 配置说明

### 环境变量

**后端配置** (`docker-compose.yml`):
- `SPRING_PROFILES_ACTIVE=docker` - Spring配置文件
- `SERVER_PORT=8080` - 服务端口
- `SPRING_DATASOURCE_URL` - 数据库连接URL
- `SPRING_H2_CONSOLE_ENABLED=true` - 启用H2控制台

**前端配置**:
- `NODE_ENV=production` - 生产环境
- `NEXT_PUBLIC_API_URL=http://localhost:8080` - API地址

### 数据持久化

- 数据存储在 `./data` 目录中
- 使用H2数据库文件存储
- 数据在容器重启后保持

## 🐛 故障排除

### 常见问题

1. **端口冲突**
   ```bash
   # 检查端口占用
   lsof -i :3000
   lsof -i :8080
   
   # 修改端口（编辑docker-compose.yml）
   ports:
     - "3001:3000"  # 前端
     - "8081:8080"  # 后端
   ```

2. **服务启动失败**
   ```bash
   # 查看详细日志
   docker-compose logs backend
   docker-compose logs frontend
   
   # 检查容器状态
   docker-compose ps
   ```

3. **数据丢失**
   ```bash
   # 检查数据目录权限
   ls -la ./data
   
   # 重新创建数据目录
   mkdir -p ./data
   chmod 755 ./data
   ```

### 健康检查

```bash
# 检查后端健康状态
curl http://localhost:8080/actuator/health

# 检查前端状态
curl http://localhost:3000

# 查看容器健康状态
docker-compose ps
```

## 🔄 更新部署

```bash
# 1. 停止服务
docker-compose down

# 2. 拉取最新代码
git pull

# 3. 重新构建并启动
docker-compose up -d --build
```

## 🗑️ 完全清理

```bash
# 停止并删除所有相关资源
docker-compose down --rmi all --volumes

# 删除数据目录
rm -rf ./data

# 清理Docker系统
docker system prune -a
```

## 📊 监控和日志

### 查看实时日志

```bash
# 所有服务日志
docker-compose logs -f

# 特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend
```

### 资源监控

```bash
# 查看容器资源使用
docker stats

# 查看特定容器资源使用
docker stats accounting-backend accounting-frontend
```

## 🛡️ 安全注意事项

1. **生产环境部署**时请修改默认配置
2. **数据库密码**应设置强密码
3. **网络访问**应限制在必要端口
4. **定期备份**数据文件

## 📞 技术支持

如遇到问题，请检查：
1. Docker Desktop是否正常运行
2. 端口是否被占用
3. 系统资源是否充足
4. 网络连接是否正常

---

**祝您使用愉快！** 🎉
