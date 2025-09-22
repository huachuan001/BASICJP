#!/bin/bash

# Docker 启动脚本 - 网络问题修复版
echo "🚀 启动记账管理系统 Docker 环境（网络问题修复版）..."
echo "================================================"

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ 错误: Docker 未运行，请先启动 Docker Desktop"
    exit 1
fi

# 创建数据目录
echo "📁 创建数据目录..."
mkdir -p ./data

# 停止并删除现有容器
echo "🧹 清理现有容器..."
docker-compose down --remove-orphans 2>/dev/null || true

# 尝试方案1：使用原始配置
echo "🔨 尝试方案1：使用原始Docker配置..."
if docker-compose build --no-cache > /dev/null 2>&1; then
    echo "✅ 原始配置构建成功"
    echo "🚀 启动服务..."
    docker-compose up -d
    echo "✅ 服务启动完成！"
    echo "🌐 前端: http://localhost:3000"
    echo "🔧 后端: http://localhost:8080"
    exit 0
else
    echo "❌ 原始配置构建失败，尝试离线构建..."
fi

# 尝试方案2：使用离线构建
echo "🔨 尝试方案2：使用离线构建配置..."
if docker-compose -f docker-compose-offline.yml build --no-cache > /dev/null 2>&1; then
    echo "✅ 离线构建成功"
    echo "🚀 启动服务..."
    docker-compose -f docker-compose-offline.yml up -d
    echo "✅ 服务启动完成！"
    echo "🌐 前端: http://localhost:3000"
    echo "🔧 后端: http://localhost:8080"
    exit 0
else
    echo "❌ 离线构建也失败"
fi

# 如果都失败，提供手动解决方案
echo ""
echo "❌ 所有Docker构建方案都失败了"
echo "================================================"
echo "🛠️  请尝试以下解决方案："
echo ""
echo "1️⃣ 重启Docker Desktop"
echo "   - 完全退出Docker Desktop"
echo "   - 重新启动Docker Desktop"
echo "   - 等待完全启动后重试"
echo ""
echo "2️⃣ 配置镜像加速器"
echo "   - 打开Docker Desktop"
echo "   - 进入Settings > Docker Engine"
echo "   - 添加镜像加速器配置"
echo ""
echo "3️⃣ 检查网络连接"
echo "   - 尝试使用VPN"
echo "   - 更换网络环境"
echo "   - 检查代理设置"
echo ""
echo "4️⃣ 使用本地开发环境"
echo "   - 后端: cd backend && ./mvnw spring-boot:run"
echo "   - 前端: cd frontend && npm install && npm run dev"
echo ""
echo "📖 详细解决方案请查看: DOCKER_NETWORK_FIX.md"
