#!/bin/bash

# Docker 测试脚本
echo "🧪 测试Docker配置..."
echo "===================="

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker未运行，请启动Docker Desktop"
    exit 1
fi

echo "✅ Docker运行正常"

# 检查Docker Compose配置
echo "🔍 验证Docker Compose配置..."
if docker-compose config > /dev/null 2>&1; then
    echo "✅ Docker Compose配置有效"
else
    echo "❌ Docker Compose配置有误"
    exit 1
fi

# 检查Dockerfile语法
echo "🔍 检查Dockerfile..."
if docker build --dry-run ./backend > /dev/null 2>&1; then
    echo "✅ 后端Dockerfile语法正确"
else
    echo "⚠️  后端Dockerfile可能有问题"
fi

if docker build --dry-run ./frontend > /dev/null 2>&1; then
    echo "✅ 前端Dockerfile语法正确"
else
    echo "⚠️  前端Dockerfile可能有问题"
fi

echo ""
echo "🎉 Docker配置测试完成！"
echo "===================="
echo "💡 现在可以运行: ./docker-start.sh"
