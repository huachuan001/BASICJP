#!/bin/bash

# Docker 网络问题修复脚本
echo "🔧 修复Docker网络问题..."
echo "=========================="

# 1. 检查Docker状态
echo "1️⃣ 检查Docker状态..."
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker未运行，请启动Docker Desktop"
    exit 1
fi
echo "✅ Docker运行正常"

# 2. 测试网络连接
echo "2️⃣ 测试网络连接..."
if ping -c 1 8.8.8.8 > /dev/null 2>&1; then
    echo "✅ 网络连接正常"
else
    echo "⚠️  网络连接可能有问题"
fi

# 3. 清理Docker缓存
echo "3️⃣ 清理Docker缓存..."
docker system prune -f
echo "✅ Docker缓存已清理"

# 4. 测试拉取镜像
echo "4️⃣ 测试拉取基础镜像..."
echo "尝试拉取 node:18-slim..."
if docker pull node:18-slim > /dev/null 2>&1; then
    echo "✅ node:18-slim 拉取成功"
else
    echo "❌ node:18-slim 拉取失败"
fi

echo "尝试拉取 openjdk:17-slim..."
if docker pull openjdk:17-slim > /dev/null 2>&1; then
    echo "✅ openjdk:17-slim 拉取成功"
else
    echo "❌ openjdk:17-slim 拉取失败"
fi

echo "尝试拉取 maven:3.9-openjdk-17-slim..."
if docker pull maven:3.9-openjdk-17-slim > /dev/null 2>&1; then
    echo "✅ maven:3.9-openjdk-17-slim 拉取成功"
else
    echo "❌ maven:3.9-openjdk-17-slim 拉取失败"
fi

# 5. 提供解决方案
echo ""
echo "🛠️  如果镜像拉取失败，请尝试以下解决方案："
echo "=========================="
echo "1. 重启Docker Desktop"
echo "2. 检查网络代理设置"
echo "3. 使用VPN或更换网络"
echo "4. 配置Docker镜像加速器"
echo ""
echo "💡 配置镜像加速器："
echo "   - 打开Docker Desktop"
echo "   - 进入Settings > Docker Engine"
echo "   - 添加以下配置："
echo '   "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"]'
echo ""
echo "🔍 当前Docker配置："
cat ~/.docker/daemon.json 2>/dev/null || echo "未找到daemon.json配置文件"
