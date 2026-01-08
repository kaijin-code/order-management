# 订单管理系统

## 技术栈

- **前端**: Next.js 16 + React 19 + Tailwind CSS 4
- **后端**: Next.js API Routes (App Router)
- **数据库**: SQLite (better-sqlite3)
- **认证**: JWT + bcrypt
- **微服务通信**: gRPC (与外部优惠券服务交互)

## 核心功能

### 用户认证
- 注册/登录 (`/api/auth/register`, `/api/auth/login`)
- JWT Token 认证，7天有效期

### 订单管理
- 创建订单 (支持优惠券抵扣)
- 查看用户订单列表
- 订单状态管理

### 优惠券系统 (通过 gRPC 调用外部 Java 服务)
- 获取可用优惠券模板
- 领取优惠券
- 验证/使用/退还优惠券
- 支持满减、折扣、立减三种类型

## 架构特点

- **前后端一体**: Next.js 全栈方案
- **微服务集成**: 订单服务 (Node.js) + 优惠券服务 (Java/gRPC)
- **数据库设计**: users 表 + orders 表，支持优惠券字段迁移

## 配置说明

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| 开发端口 | 3001 | `npm run dev` |
| gRPC 服务地址 | `localhost:9090` | 环境变量 `COUPON_SERVICE_URL` |
| JWT 密钥 | - | 环境变量 `JWT_SECRET` (生产环境必须设置) |

## 快速开始

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 启动生产服务器
npm start
```

## 项目结构

```
src/
├── app/
│   ├── api/
│   │   ├── auth/          # 认证接口
│   │   ├── coupons/       # 优惠券接口
│   │   └── orders/        # 订单接口
│   └── page.tsx           # 主页面
├── components/            # React 组件
├── grpc/                  # gRPC 客户端
│   ├── client.ts
│   └── protos/
└── lib/                   # 工具库
    ├── auth.ts            # 认证工具
    └── db.ts              # 数据库连接

docs/coupon-service-java/  # Java 优惠券服务参考实现
```
