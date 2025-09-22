# Docker 网络问题解决方案

## 🚨 问题描述

Docker构建时出现网络连接问题，无法从Docker Hub拉取基础镜像：
```
failed to resolve source metadata for docker.io/library/node:18-alpine: failed to do request: Head "https://registry-1.docker.io/v2/library/node/manifests/18-alpine": EOF
```

## 🔧 解决方案

### 方案1：重启Docker Desktop（推荐）

1. 完全退出Docker Desktop
2. 重新启动Docker Desktop
3. 等待Docker完全启动后重试

```bash
# 重启后测试
docker pull node:18-slim
docker pull openjdk:17-slim
```

### 方案2：配置Docker镜像加速器

1. 打开Docker Desktop
2. 进入 **Settings** > **Docker Engine**
3. 在JSON配置中添加：

```json
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}
```

4. 点击 **Apply & Restart**

### 方案3：使用离线构建（临时方案）

如果网络问题持续，可以使用离线构建版本：

```bash
# 使用离线构建配置
docker-compose -f docker-compose-offline.yml up -d --build
```

### 方案4：检查网络代理

如果使用代理，确保Docker配置正确：

1. 检查Docker Desktop代理设置
2. 确保代理服务器可访问Docker Hub
3. 尝试关闭代理后重试

### 方案5：使用VPN或更换网络

1. 尝试使用VPN连接
2. 更换网络环境（如手机热点）
3. 在非高峰时段重试

## 🧪 测试网络连接

运行网络诊断脚本：

```bash
./fix-docker-network.sh
```

## 📋 验证步骤

1. **测试基础镜像拉取**：
   ```bash
   docker pull node:18-slim
   docker pull openjdk:17-slim
   docker pull maven:3.9-openjdk-17-slim
   ```

2. **测试项目构建**：
   ```bash
   docker-compose config
   docker-compose build
   ```

3. **启动服务**：
   ```bash
   docker-compose up -d
   ```

## 🆘 如果所有方案都失败

### 使用本地开发环境

如果Docker网络问题无法解决，可以回退到本地开发：

```bash
# 后端启动
cd backend
./mvnw spring-boot:run

# 前端启动（新终端）
cd frontend
npm install
npm run dev
```

### 联系技术支持

如果问题持续存在，可能需要：
1. 检查公司网络策略
2. 联系IT部门配置Docker代理
3. 使用企业内网镜像仓库

## 📞 常见问题

**Q: 为什么会出现网络连接问题？**
A: 可能原因包括网络代理、防火墙、DNS解析、Docker Hub服务状态等。

**Q: 镜像加速器配置后仍然失败？**
A: 尝试多个镜像源，或等待网络恢复后重试。

**Q: 离线构建有什么限制？**
A: 离线构建使用单阶段构建，镜像体积较大，仅作为临时解决方案。

---

**建议优先尝试方案1和方案2，通常可以解决大部分网络问题。** 🚀
