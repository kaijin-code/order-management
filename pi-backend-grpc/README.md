# Coupon Service (Java/Spring Boot)

gRPC 优惠券微服务参考实现

表结构如下：
-- 优惠券模板表（定义优惠券规则）
CREATE TABLE coupon_template (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 name VARCHAR(100) NOT NULL COMMENT '优惠券名称',
                                 type TINYINT NOT NULL COMMENT '类型: 1-满减 2-折扣 3-立减',
                                 discount_value DECIMAL(10,2) NOT NULL COMMENT '优惠值(满减金额/折扣比例/立减金额)',
                                 min_amount DECIMAL(10,2) DEFAULT 0 COMMENT '最低消费金额',
                                 max_discount DECIMAL(10,2) DEFAULT NULL COMMENT '最大优惠金额(折扣券用)',
                                 total_count INT NOT NULL COMMENT '发放总量',
                                 remain_count INT NOT NULL COMMENT '剩余数量',
                                 start_time DATETIME NOT NULL COMMENT '生效开始时间',
                                 end_time DATETIME NOT NULL COMMENT '生效结束时间',
                                 status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
                                 created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 INDEX idx_status_time (status, start_time, end_time)
) COMMENT '优惠券模板表';

-- 用户优惠券表（用户领取的优惠券）
CREATE TABLE user_coupon (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             user_id BIGINT NOT NULL COMMENT '用户ID',
                             template_id BIGINT NOT NULL COMMENT '优惠券模板ID',
                             coupon_code VARCHAR(32) UNIQUE NOT NULL COMMENT '优惠券码',
                             status TINYINT DEFAULT 0 COMMENT '状态: 0-未使用 1-已使用 2-已过期',
                             used_order_id BIGINT DEFAULT NULL COMMENT '使用的订单ID',
                             used_at DATETIME DEFAULT NULL COMMENT '使用时间',
                             expire_time DATETIME NOT NULL COMMENT '过期时间',
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             INDEX idx_user_status (user_id, status),
                             INDEX idx_coupon_code (coupon_code),
                             FOREIGN KEY (template_id) REFERENCES coupon_template(id)
) COMMENT '用户优惠券表';

-- 优惠券使用记录表（审计日志）
CREATE TABLE coupon_usage_log (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  user_coupon_id BIGINT NOT NULL,
                                  order_id BIGINT NOT NULL,
                                  order_amount DECIMAL(10,2) NOT NULL COMMENT '订单原金额',
                                  discount_amount DECIMAL(10,2) NOT NULL COMMENT '优惠金额',
                                  final_amount DECIMAL(10,2) NOT NULL COMMENT '最终金额',
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  INDEX idx_order (order_id)
) COMMENT '优惠券使用记录';

新用户注册送优惠券，下单时校验可用优惠券，并按金额高低排序，默认选中金额最高的

## 技术栈

- Java 17+
- Spring Boot 3.x
- grpc-spring-boot-starter
- MyBatis Plus / JPA
- MySQL 8.x
- Redis (缓存 & 分布式锁)

## 项目结构

```
coupon-service/
├── src/main/java/com/example/coupon/
│   ├── CouponServiceApplication.java
│   ├── grpc/
│   │   └── CouponGrpcService.java      # gRPC 服务实现
│   ├── service/
│   │   ├── CouponService.java          # 业务接口
│   │   └── impl/
│   │       └── CouponServiceImpl.java  # 业务实现
│   ├── repository/
│   │   ├── CouponTemplateRepository.java
│   │   ├── UserCouponRepository.java
│   │   └── CouponUsageLogRepository.java
│   ├── entity/
│   │   ├── CouponTemplate.java
│   │   ├── UserCoupon.java
│   │   └── CouponUsageLog.java
│   └── util/
│       └── CouponCodeGenerator.java
├── src/main/proto/
│   └── coupon.proto                    # 与 Next.js 共享
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/                   # Flyway 迁移脚本
└── pom.xml
```

## 核心依赖 (pom.xml)

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    
    <!-- gRPC Spring Boot Starter -->
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-spring-boot-starter</artifactId>
        <version>2.15.0.RELEASE</version>
    </dependency>
    
    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    
    <!-- MyBatis Plus -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.5</version>
    </dependency>
    
    <!-- Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
</dependencies>
```

## 配置 (application.yml)

```yaml
server:
  port: 8080

grpc:
  server:
    port: 9091

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/coupon_db?useSSL=false&serverTimezone=UTC
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  redis:
    host: localhost
    port: 6379
```

## 启动服务

```bash
mvn spring-boot:run
```

gRPC 服务将在 `localhost:9091` 启动
