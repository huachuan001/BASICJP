# 记账管理系统 - 文件结构分析

## 项目概述

这是一个基于Spring Boot + Next.js的前后端分离记账管理系统，采用现代化的技术栈，提供直观的用户界面和稳定的后端服务。

## 📁 根目录文件

### 文档文件
| 文件名 | 作用 | 可删除 | 说明 |
|--------|------|--------|------|
| `README.md` | 项目主说明文档，包含安装、使用、API接口说明 | ❌ | **核心文档**，项目介绍和用户指南 |
| `Prd.md` | 产品需求文档，定义系统功能和技术方案 | ❌ | **重要文档**，项目需求定义 |
| `Tech.md` | 技术文档，详细的技术架构和实现说明 | ❌ | **重要文档**，技术实现参考 |

### 配置文件
| 文件名 | 作用 | 可删除 | 说明 |
|--------|------|--------|------|
| `docker-compose.yml` | Docker容器编排配置，用于一键启动前后端服务 | ❌ | **重要配置**，容器化部署必需 |
| `start.sh` | 启动脚本，自动启动前后端服务 | ❌ | **重要脚本**，简化启动流程 |

---

## 📁 后端文件 (backend/)

### 核心源代码
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `src/main/java/com/accounting/AccountingApplication.java` | Spring Boot主启动类 | ❌ | **核心文件**，应用入口 |
| `src/main/java/com/accounting/controller/AccountingController.java` | REST API控制器，处理HTTP请求 | ❌ | **核心文件**，API接口实现 |
| `src/main/java/com/accounting/service/AccountingService.java` | 业务逻辑服务层 | ❌ | **核心文件**，业务逻辑处理 |
| `src/main/java/com/accounting/service/CsvStorageService.java` | CSV文件存储服务 | ❌ | **核心文件**，数据持久化 |
| `src/main/java/com/accounting/model/AccountingRecord.java` | 记账记录数据模型 | ❌ | **核心文件**，数据实体 |
| `src/main/java/com/accounting/model/PageResponse.java` | 分页响应数据模型 | ❌ | **核心文件**，分页数据结构 |

### 配置文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `pom.xml` | Maven项目配置文件，定义依赖和构建配置 | ❌ | **核心配置**，项目构建必需 |
| `src/main/resources/application.properties` | Spring Boot应用配置 | ❌ | **核心配置**，应用运行配置 |
| `Dockerfile` | 后端Docker镜像构建文件 | ❌ | **重要配置**，容器化部署必需 |

### 数据文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `data.csv` | 记账数据存储文件 | ❌ | **核心数据**，用户数据存储 |

### 构建输出文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `target/` | Maven编译输出目录 | ✅ | **可删除**，构建产物，可重新生成 |
| `target/accounting-system-1.0.0.jar` | 编译后的可执行JAR包 | ✅ | **可删除**，可通过`mvn package`重新生成 |
| `target/classes/` | 编译后的class文件 | ✅ | **可删除**，编译产物 |
| `target/generated-sources/` | 生成的源代码 | ✅ | **可删除**，自动生成文件 |
| `target/maven-archiver/` | Maven归档文件 | ✅ | **可删除**，构建产物 |

---

## 📁 前端文件 (frontend/)

### 核心源代码
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `src/app/page.tsx` | 主页面组件 | ❌ | **核心文件**，应用主界面 |
| `src/app/layout.tsx` | 根布局组件 | ❌ | **核心文件**，页面布局 |
| `src/components/AddRecordForm.tsx` | 添加记录表单组件 | ❌ | **核心文件**，添加功能 |
| `src/components/RecordList.tsx` | 记录列表组件 | ❌ | **核心文件**，列表显示 |
| `src/components/SumDisplay.tsx` | 总金额显示组件 | ❌ | **核心文件**，统计功能 |
| `src/services/api.ts` | API服务层 | ❌ | **核心文件**，后端通信 |
| `src/types/accounting.ts` | TypeScript类型定义 | ❌ | **核心文件**，类型安全 |

### 配置文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `package.json` | npm项目配置文件 | ❌ | **核心配置**，依赖管理 |
| `package-lock.json` | 依赖锁定文件 | ❌ | **重要配置**，确保依赖版本一致 |
| `tsconfig.json` | TypeScript编译配置 | ❌ | **核心配置**，TS编译必需 |
| `next.config.ts` | Next.js框架配置 | ❌ | **核心配置**，框架配置 |
| `eslint.config.mjs` | ESLint代码检查配置 | ❌ | **重要配置**，代码质量保证 |
| `postcss.config.mjs` | PostCSS配置 | ❌ | **重要配置**，CSS处理 |
| `Dockerfile` | 前端Docker镜像构建文件 | ❌ | **重要配置**，容器化部署 |

### 样式文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `src/app/globals.css` | 全局样式文件 | ❌ | **核心文件**，应用样式 |

### 类型定义文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `next-env.d.ts` | Next.js类型定义 | ❌ | **重要文件**，TypeScript支持 |

### 静态资源
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `public/file.svg` | 文件图标 | ✅ | **可删除**，默认图标，未使用 |
| `public/globe.svg` | 地球图标 | ✅ | **可删除**，默认图标，未使用 |
| `public/next.svg` | Next.js图标 | ✅ | **可删除**，默认图标，未使用 |
| `public/vercel.svg` | Vercel图标 | ✅ | **可删除**，默认图标，未使用 |
| `public/window.svg` | 窗口图标 | ✅ | **可删除**，默认图标，未使用 |
| `src/app/favicon.ico` | 网站图标 | ❌ | **重要文件**，浏览器标签图标 |

### 构建输出和依赖
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `node_modules/` | npm依赖包目录 | ✅ | **可删除**，可通过`npm install`重新安装 |
| `.next/` | Next.js构建输出 | ✅ | **可删除**，可通过`npm run build`重新生成 |

### 文档文件
| 文件路径 | 作用 | 可删除 | 说明 |
|----------|------|--------|------|
| `README.md` | Next.js默认说明文档 | ✅ | **可删除**，默认模板文档，已被根目录README替代 |

---

## 🗑️ 可安全删除的文件清单

### 后端可删除文件
```
backend/target/                    # 整个target目录
backend/target/accounting-system-1.0.0.jar
backend/target/accounting-system-1.0.0.jar.original
backend/target/classes/
backend/target/generated-sources/
backend/target/maven-archiver/
backend/target/maven-status/
backend/target/test-classes/
```

### 前端可删除文件
```
frontend/node_modules/             # 依赖包目录
frontend/.next/                    # 构建输出目录
frontend/public/file.svg           # 未使用的默认图标
frontend/public/globe.svg          # 未使用的默认图标
frontend/public/next.svg           # 未使用的默认图标
frontend/public/vercel.svg         # 未使用的默认图标
frontend/public/window.svg         # 未使用的默认图标
frontend/README.md                 # 默认模板文档
```

## ⚠️ 重要说明

1. **数据文件**：`backend/data.csv` 包含用户数据，删除会丢失所有记账记录
2. **构建产物**：删除target和node_modules后，需要重新编译/安装依赖
3. **配置文件**：所有配置文件都是必需的，删除会导致应用无法正常运行
4. **源代码**：所有源代码文件都是核心功能实现，不可删除

## 🔄 重新生成方法

删除构建产物后，可通过以下命令重新生成：

```bash
# 后端重新编译
cd backend
mvn clean package

# 前端重新安装依赖和构建
cd frontend
npm install
npm run build
```

## 📊 文件统计

- **总文件数**：约50+个文件
- **核心文件**：30+个（不可删除）
- **可删除文件**：20+个（主要是构建产物和默认文件）
- **数据文件**：1个（`data.csv`，包含用户数据）

## 🎯 清理建议

1. **定期清理**：可以定期删除构建产物以节省空间
2. **备份数据**：删除任何文件前先备份`data.csv`
3. **版本控制**：建议将`data.csv`加入`.gitignore`，避免提交用户数据
4. **依赖管理**：保持`package-lock.json`和`pom.xml`的版本一致性

---

**文档版本**: v1.0.0  
**最后更新**: 2025-01-27  
**分析工具**: AI代码分析助手
