# 记账管理系统 - 技术文档

## 项目概述

记账管理系统是一个基于前后端分离架构的Web应用程序，用于管理个人或小团队的记账记录。系统采用现代化的技术栈，提供直观的用户界面和稳定的后端服务。

## 技术架构

### 整体架构
- **架构模式**: 前后端分离 (Frontend-Backend Separation)
- **通信方式**: RESTful API (HTTP/JSON)
- **数据存储**: 文件系统 (CSV格式)
- **部署方式**: 支持传统部署和Docker容器化

### 技术栈详情

#### 后端技术栈
- **框架**: Spring Boot 3.2.0
- **语言**: Java 17
- **构建工具**: Maven 3.9+
- **数据存储**: CSV文件 (data.csv)
- **Web服务器**: 内嵌Tomcat
- **依赖管理**: Maven Central Repository

#### 前端技术栈
- **框架**: Next.js 14 (React 18)
- **语言**: TypeScript
- **样式**: Tailwind CSS
- **构建工具**: Next.js内置构建系统
- **包管理**: npm

## 系统设计

### 数据模型

#### AccountingRecord (记账记录)
```java
public class AccountingRecord {
    private Long id;           // 记录ID
    private LocalDate date;    // 日期
    private BigDecimal amount; // 金额
    private String content;    // 内容描述
}
```

#### PageResponse (分页响应)
```java
public class PageResponse<T> {
    private List<T> content;      // 数据内容
    private int page;             // 当前页码
    private int size;             // 每页大小
    private long totalElements;   // 总元素数
    private int totalPages;       // 总页数
}
```

### API接口设计

#### 1. 获取记录列表
- **端点**: `GET /api/accounting/records`
- **参数**: 
  - `page` (可选): 页码，默认0
  - `size` (可选): 每页大小，默认10
- **响应**: PageResponse<AccountingRecord>

#### 2. 添加记录
- **端点**: `POST /api/accounting/add`
- **请求体**: AccountingRecord (不含id)
- **响应**: 操作结果消息

#### 3. 删除记录
- **端点**: `DELETE /api/accounting/delete/{id}`
- **参数**: id (路径参数)
- **响应**: 操作结果消息

#### 4. 计算总和
- **端点**: `GET /api/accounting/sum`
- **响应**: 总金额数值

### 数据存储设计

#### CSV文件格式
```
日期,金额,内容
2025-09-22,100.50,测试记账
2025-09-23,200.00,购买商品
```

#### 文件操作
- **读取**: 启动时加载所有记录到内存
- **写入**: 每次修改后重写整个文件
- **备份**: 支持数据持久化到本地文件系统

## 项目结构

```
basicJp/
├── backend/                    # Spring Boot后端
│   ├── src/main/java/com/accounting/
│   │   ├── AccountingApplication.java      # 主应用类
│   │   ├── controller/                     # 控制器层
│   │   │   └── AccountingController.java
│   │   ├── service/                        # 服务层
│   │   │   ├── AccountingService.java
│   │   │   └── CsvStorageService.java
│   │   └── model/                          # 数据模型
│   │       ├── AccountingRecord.java
│   │       └── PageResponse.java
│   ├── src/main/resources/
│   │   └── application.properties          # 配置文件
│   ├── src/test/java/                      # 测试代码
│   ├── target/                             # 编译输出
│   ├── pom.xml                             # Maven配置
│   └── Dockerfile                          # Docker配置
├── frontend/                   # Next.js前端
│   ├── src/
│   │   ├── app/                            # Next.js App Router
│   │   │   ├── page.tsx                    # 主页面
│   │   │   └── layout.tsx                  # 布局组件
│   │   ├── components/                     # React组件
│   │   │   ├── RecordList.tsx              # 记录列表
│   │   │   ├── AddRecordForm.tsx           # 添加表单
│   │   │   └── SumDisplay.tsx              # 总金额显示
│   │   ├── services/                       # API服务
│   │   │   └── api.ts                      # API客户端
│   │   └── types/                          # TypeScript类型
│   │       └── accounting.ts               # 数据类型定义
│   ├── public/                             # 静态资源
│   ├── node_modules/                       # 依赖包
│   ├── .next/                              # 构建输出
│   ├── package.json                        # 包配置
│   ├── next.config.js                      # Next.js配置
│   ├── tailwind.config.js                  # Tailwind配置
│   └── Dockerfile                          # Docker配置
├── data.csv                    # 数据文件
├── start.sh                    # 启动脚本
├── docker-compose.yml         # Docker编排
├── .gitignore                 # Git忽略文件
├── README.md                  # 项目说明
├── Tech.md                    # 技术文档
└── Prd.md                     # 需求文档
```

## 核心功能实现

### 后端核心功能

#### 1. CSV存储服务 (CsvStorageService)
- **功能**: 处理CSV文件的读写操作
- **特性**: 
  - 自动创建文件
  - 数据持久化
  - 错误处理
  - 线程安全

#### 2. 业务服务 (AccountingService)
- **功能**: 处理业务逻辑
- **特性**:
  - 分页处理
  - 数据验证
  - 业务规则

#### 3. REST控制器 (AccountingController)
- **功能**: 处理HTTP请求
- **特性**:
  - CORS支持
  - 错误处理
  - 统一响应格式

### 前端核心功能

#### 1. 主页面 (page.tsx)
- **功能**: 应用主入口
- **特性**:
  - 状态管理
  - 组件协调
  - 错误处理

#### 2. 记录列表 (RecordList.tsx)
- **功能**: 显示记账记录
- **特性**:
  - 分页显示
  - 删除操作
  - 响应式设计

#### 3. 添加表单 (AddRecordForm.tsx)
- **功能**: 添加新记录
- **特性**:
  - 表单验证
  - 实时反馈
  - 数据提交

#### 4. 总金额显示 (SumDisplay.tsx)
- **功能**: 显示总金额
- **特性**:
  - 实时计算
  - 格式化显示
  - 刷新功能

## 开发环境配置

### 环境要求
- **Java**: 17+
- **Node.js**: 18+
- **Maven**: 3.6+
- **操作系统**: macOS/Linux/Windows

### 开发工具推荐
- **IDE**: IntelliJ IDEA / VS Code
- **版本控制**: Git
- **API测试**: Postman / curl
- **浏览器**: Chrome / Firefox

## 部署方案

### 1. 传统部署
```bash
# 启动后端
cd backend
java -jar target/accounting-system-1.0.0.jar

# 启动前端
cd frontend
npm run dev
```

### 2. Docker部署
```bash
# 使用Docker Compose
docker-compose up -d

# 或分别构建
docker build -t accounting-backend ./backend
docker build -t accounting-frontend ./frontend
```

### 3. 生产环境建议
- 使用Nginx作为反向代理
- 配置HTTPS证书
- 设置数据备份策略
- 监控应用性能

## 性能优化

### 后端优化
- 使用连接池管理数据库连接
- 实现缓存机制
- 优化CSV文件读写
- 添加请求限流

### 前端优化
- 代码分割和懒加载
- 图片优化
- 缓存策略
- 压缩静态资源

## 安全考虑

### 数据安全
- 输入验证和过滤
- SQL注入防护
- XSS攻击防护
- CSRF保护

### 文件安全
- 文件权限控制
- 备份策略
- 数据加密

## 扩展性设计

### 功能扩展
- 用户认证系统
- 数据分类管理
- 报表统计功能
- 数据导入导出

### 技术扩展
- 数据库迁移 (MySQL/PostgreSQL)
- 微服务架构
- 消息队列
- 分布式部署

## 测试策略

### 单元测试
- 后端服务层测试
- 前端组件测试
- 工具函数测试

### 集成测试
- API接口测试
- 前后端集成测试
- 数据流测试

### 性能测试
- 负载测试
- 压力测试
- 响应时间测试

## 监控和日志

### 应用监控
- 健康检查端点
- 性能指标收集
- 错误日志记录

### 日志管理
- 结构化日志
- 日志级别控制
- 日志轮转策略

## 版本历史

### v1.0.0 (2025-09-22)
- 初始版本发布
- 基础记账功能
- 前后端分离架构
- CSV数据存储

## 维护说明

### 日常维护
- 定期备份数据文件
- 监控应用状态
- 更新依赖版本
- 性能优化

### 故障处理
- 日志分析
- 错误排查
- 数据恢复
- 服务重启

---

**文档版本**: v1.0.0  
**最后更新**: 2025-09-22  
**维护人员**: 开发团队
