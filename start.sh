#!/bin/bash

# 记账管理系统启动脚本

echo "启动记账管理系统..."

# 检查Java是否安装
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java，请先安装Java 17或更高版本"
    exit 1
fi

# 检查Node.js是否安装
if ! command -v node &> /dev/null; then
    echo "错误: 未找到Node.js，请先安装Node.js"
    exit 1
fi

# 启动后端
echo "启动后端服务..."
cd backend
if [ ! -f "target/accounting-system-1.0.0.jar" ]; then
    echo "编译后端项目..."
    mvn clean package -DskipTests
fi

# 在后台启动后端
java -jar target/accounting-system-1.0.0.jar &
BACKEND_PID=$!

# 等待后端启动
echo "等待后端启动..."
sleep 10

# 检查后端是否启动成功
if ! curl -s http://localhost:8080/api/accounting/records > /dev/null; then
    echo "错误: 后端启动失败"
    kill $BACKEND_PID
    exit 1
fi

echo "后端启动成功！"

# 启动前端
echo "启动前端服务..."
cd ../frontend

# 安装依赖（如果需要）
if [ ! -d "node_modules" ]; then
    echo "安装前端依赖..."
    npm install
fi

# 在后台启动前端
npm run dev &
FRONTEND_PID=$!

# 等待前端启动
echo "等待前端启动..."
sleep 10

echo "=========================================="
echo "记账管理系统启动完成！"
echo "前端地址: http://localhost:3000"
echo "后端API: http://localhost:8080"
echo "=========================================="
echo "按 Ctrl+C 停止服务"

# 等待用户中断
trap "echo '正在停止服务...'; kill $BACKEND_PID $FRONTEND_PID; exit" INT
wait
