# Coupon Service (Java/Spring Boot)

gRPC 优惠券微服务参考实现

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
