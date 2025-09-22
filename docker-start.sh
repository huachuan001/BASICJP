#!/bin/bash

# 记账管理系统 Docker 启动脚本
# 作者: AI Assistant
# 日期: 2025-09-22

set -e

echo "🚀 启动记账管理系统 Docker 环境..."
echo "=================================="

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ 错误: Docker 未运行，请先启动 Docker Desktop"
    exit 1
fi

# 检查Docker Compose是否可用
if ! command -v docker-compose &> /dev/null; then
    echo "❌ 错误: Docker Compose 未安装"
    exit 1
fi

# 创建数据目录
echo "📁 创建数据目录..."
mkdir -p ./data

# 停止并删除现有容器
echo "🧹 清理现有容器..."
docker-compose down --remove-orphans

# 构建并启动服务
echo "🔨 构建Docker镜像..."
docker-compose build --no-cache

echo "🚀 启动服务..."
docker-compose up -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 10

# 检查服务状态
echo "🔍 检查服务状态..."
docker-compose ps

# 显示访问信息
echo ""
echo "✅ 服务启动完成！"
echo "=================================="
echo "🌐 前端访问地址: http://localhost:3000"
echo "🔧 后端API地址: http://localhost:8080"
echo "📊 后端健康检查: http://localhost:8080/actuator/health"
echo "🗄️  H2数据库控制台: http://localhost:8080/h2-console"
echo ""
echo "📝 常用命令:"
echo "  查看日志: docker-compose logs -f"
echo "  停止服务: docker-compose down"
echo "  重启服务: docker-compose restart"
echo "  查看状态: docker-compose ps"
echo "=================================="
