#!/bin/bash

# 本地开发环境启动脚本
echo "🚀 启动记账管理系统本地开发环境..."
echo "=================================="

# 检查Java环境
echo "1️⃣ 检查Java环境..."
if ! command -v java &> /dev/null; then
    echo "❌ Java未安装，请先安装Java 17"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven未安装，请先安装Maven"
    exit 1
fi

if ! command -v node &> /dev/null; then
    echo "❌ Node.js未安装，请先安装Node.js"
    exit 1
fi

echo "✅ 环境检查通过"

# 创建数据目录
echo "2️⃣ 创建数据目录..."
mkdir -p ./data

# 启动后端
echo "3️⃣ 启动后端服务..."
cd backend
echo "正在启动Spring Boot应用..."
nohup ./mvnw spring-boot:run > ../logs/backend.log 2>&1 &
BACKEND_PID=$!
echo "后端服务PID: $BACKEND_PID"

# 等待后端启动
echo "4️⃣ 等待后端服务启动..."
sleep 10

# 检查后端是否启动成功
if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ 后端服务启动成功"
else
    echo "⚠️  后端服务可能还在启动中，请稍等..."
fi

# 启动前端
echo "5️⃣ 启动前端服务..."
cd ../frontend

# 安装依赖
if [ ! -d "node_modules" ]; then
    echo "安装前端依赖..."
    npm install
fi

echo "正在启动Next.js应用..."
nohup npm run dev > ../logs/frontend.log 2>&1 &
FRONTEND_PID=$!
echo "前端服务PID: $FRONTEND_PID"

# 等待前端启动
echo "6️⃣ 等待前端服务启动..."
sleep 5

# 创建日志目录
mkdir -p ../logs

# 显示启动信息
echo ""
echo "✅ 本地开发环境启动完成！"
echo "=========================="
echo "🌐 前端访问地址: http://localhost:3000"
echo "🔧 后端API地址: http://localhost:8080"
echo "📊 后端健康检查: http://localhost:8080/actuator/health"
echo "🗄️  H2数据库控制台: http://localhost:8080/h2-console"
echo ""
echo "📝 服务进程:"
echo "  后端PID: $BACKEND_PID"
echo "  前端PID: $FRONTEND_PID"
echo ""
echo "📋 常用命令:"
echo "  查看后端日志: tail -f logs/backend.log"
echo "  查看前端日志: tail -f logs/frontend.log"
echo "  停止后端: kill $BACKEND_PID"
echo "  停止前端: kill $FRONTEND_PID"
echo "  停止所有服务: ./stop-local.sh"
echo "=========================="

# 保存PID到文件
echo "$BACKEND_PID" > ../logs/backend.pid
echo "$FRONTEND_PID" > ../logs/frontend.pid
