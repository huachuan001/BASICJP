#!/bin/bash

# 记账管理系统 Docker 停止脚本
# 作者: AI Assistant
# 日期: 2025-09-22

set -e

echo "🛑 停止记账管理系统 Docker 环境..."
echo "=================================="

# 停止并删除容器
echo "🧹 停止并删除容器..."
docker-compose down --remove-orphans

# 可选：删除镜像（取消注释以启用）
# echo "🗑️  删除Docker镜像..."
# docker-compose down --rmi all

# 可选：删除数据卷（取消注释以启用）
# echo "🗑️  删除数据卷..."
# docker volume prune -f

echo "✅ 服务已停止！"
echo "=================================="
echo "💡 提示: 数据已保存在 ./data 目录中"
echo "💡 如需完全清理，请运行: docker-compose down --rmi all --volumes"
