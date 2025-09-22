#!/bin/bash

# Docker 代理问题修复脚本
echo "🔧 修复Docker代理配置问题..."
echo "=============================="

# 1. 检查当前代理配置
echo "1️⃣ 检查当前Docker代理配置..."
docker info | grep -i proxy

# 2. 备份当前配置
echo "2️⃣ 备份当前Docker配置..."
if [ -f ~/.docker/daemon.json ]; then
    cp ~/.docker/daemon.json ~/.docker/daemon.json.backup
    echo "✅ 已备份到 ~/.docker/daemon.json.backup"
fi

# 3. 创建无代理配置
echo "3️⃣ 创建无代理Docker配置..."
cat > ~/.docker/daemon.json << 'EOF'
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com",
    "https://ccr.ccs.tencentyun.com"
  ],
  "insecure-registries": [],
  "debug": false,
  "experimental": false
}
EOF

echo "✅ 已创建无代理配置"

# 4. 重启Docker Desktop
echo "4️⃣ 请手动重启Docker Desktop..."
echo "   - 完全退出Docker Desktop"
echo "   - 重新启动Docker Desktop"
echo "   - 等待完全启动后按任意键继续"
read -p "按回车键继续..."

# 5. 测试网络连接
echo "5️⃣ 测试网络连接..."
if ping -c 1 8.8.8.8 > /dev/null 2>&1; then
    echo "✅ 网络连接正常"
else
    echo "❌ 网络连接有问题"
fi

# 6. 测试Docker Hub连接
echo "6️⃣ 测试Docker Hub连接..."
if curl -I https://registry-1.docker.io > /dev/null 2>&1; then
    echo "✅ Docker Hub连接正常"
else
    echo "❌ Docker Hub连接失败"
fi

# 7. 测试镜像拉取
echo "7️⃣ 测试镜像拉取..."
echo "尝试拉取 node:18-slim..."
if timeout 30 docker pull node:18-slim > /dev/null 2>&1; then
    echo "✅ node:18-slim 拉取成功"
else
    echo "❌ node:18-slim 拉取失败"
fi

echo ""
echo "🎯 修复完成！"
echo "============="
echo "如果镜像拉取成功，请运行："
echo "  ./docker-start-fixed.sh"
echo ""
echo "如果仍然失败，请尝试："
echo "  1. 使用VPN"
echo "  2. 更换网络环境"
echo "  3. 使用本地开发环境"
