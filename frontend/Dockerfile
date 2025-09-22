# 多阶段构建 - 构建阶段
FROM node:18-slim AS build

WORKDIR /app

# 复制package文件
COPY package*.json ./

# 安装所有依赖（包括devDependencies）
RUN npm ci

# 复制源代码
COPY . .

# 构建应用
RUN npm run build

# 生产阶段
FROM node:18-slim AS production

WORKDIR /app

# 复制package文件
COPY package*.json ./

# 只安装生产依赖
RUN npm ci --only=production && npm cache clean --force

# 从构建阶段复制构建产物
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public
COPY --from=build /app/next.config.ts ./

# 创建非root用户
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs
RUN chown -R nextjs:nodejs /app
USER nextjs

# 暴露端口
EXPOSE 3000

# 设置环境变量
ENV NODE_ENV=production
ENV PORT=3000

# 安装wget用于健康检查
USER root
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*
USER nextjs

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:3000 || exit 1

# 启动应用
CMD ["npm", "start"]
