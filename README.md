# 记账管理系统

一个基于Spring Boot + React/Next.js的前后端分离记账管理系统。

## 功能特性

- ✅ 添加记账记录（日期、金额、内容）
- ✅ 删除记账记录
- ✅ 计算总金额
- ✅ 分页显示记录（每页10条）
- ✅ 响应式UI设计
- ✅ 数据持久化（CSV文件存储）

## 技术栈

### 后端
- Java 17
- Spring Boot 3.2.0
- Maven
- CSV文件存储

### 前端
- React 18
- Next.js 14
- TypeScript
- Tailwind CSS

## 项目结构

```
BASICJP/
├── backend/                 # Spring Boot后端
│   ├── src/main/java/com/accounting/
│   │   ├── AccountingApplication.java    # 主应用类
│   │   ├── controller/                   # 控制器层
│   │   ├── service/                      # 服务层
│   │   └── model/                        # 数据模型
│   ├── src/main/resources/
│   │   └── application.properties        # 配置文件
│   └── pom.xml                          # Maven配置
├── frontend/                # Next.js前端
│   ├── src/
│   │   ├── app/                         # Next.js App Router
│   │   ├── components/                  # React组件
│   │   ├── services/                    # API服务
│   │   └── types/                       # TypeScript类型定义
│   └── package.json
├── start.sh                # 启动脚本
└── README.md
```

## 快速开始

### 环境要求

- Java 17+
- Node.js 18+
- Maven 3.6+

### 安装依赖

```bash
# 安装Java依赖
cd backend
mvn clean install

# 安装前端依赖
cd ../frontend
npm install
```

### 启动应用

#### 方式一：使用启动脚本（推荐）

```bash
./start.sh
```

#### 方式二：手动启动

1. 启动后端：
```bash
cd backend
mvn spring-boot:run
# 或者
java -jar target/accounting-system-1.0.0.jar
```

2. 启动前端：
```bash
cd frontend
npm run dev
```

### 访问应用

- 前端界面：http://localhost:3000
- 后端API：http://localhost:8080

## API接口

### 获取记录列表
```
GET /api/accounting/records?page=0&size=10
```

### 添加记录
```
POST /api/accounting/add
Content-Type: application/json

{
  "date": "2025-09-22",
  "amount": 100.50,
  "content": "测试记账"
}
```

### 删除记录
```
DELETE /api/accounting/delete/{id}
```

### 计算总和
```
GET /api/accounting/sum
```

## 数据存储

系统使用CSV文件（`data.csv`）进行数据持久化，文件格式：
```
日期,金额,内容
2025-09-22,100.50,测试记账
```

## 开发说明

### 后端开发

1. 修改Java代码后重新编译：
```bash
mvn clean compile
```

2. 运行测试：
```bash
mvn test
```

### 前端开发

1. 开发模式启动：
```bash
npm run dev
```

2. 构建生产版本：
```bash
npm run build
```

## 部署说明

### 后端部署

1. 构建JAR包：
```bash
mvn clean package -DskipTests
```

2. 运行JAR包：
```bash
java -jar target/accounting-system-1.0.0.jar
```

### 前端部署

1. 构建静态文件：
```bash
npm run build
```

2. 启动生产服务器：
```bash
npm start
```

## 注意事项

1. 确保Java和Node.js环境正确安装
2. 后端默认运行在8080端口，前端运行在3000端口
3. 数据文件`data.csv`会在首次添加记录时自动创建
4. 系统支持CORS跨域请求

## 故障排除

### 后端启动失败
- 检查Java版本是否为17+
- 检查8080端口是否被占用
- 查看Maven依赖是否正确下载

### 前端启动失败
- 检查Node.js版本是否为18+
- 检查3000端口是否被占用
- 运行`npm install`重新安装依赖

### API请求失败
- 确认后端服务已启动
- 检查CORS配置
- 查看浏览器控制台错误信息

## 许可证

MIT License
