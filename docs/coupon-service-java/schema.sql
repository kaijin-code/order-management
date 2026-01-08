-- Coupon Service 数据库表设计
-- MySQL 8.x

CREATE DATABASE IF NOT EXISTS coupon_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE coupon_db;

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
) ENGINE=InnoDB COMMENT '优惠券模板表';

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
    INDEX idx_expire_time (expire_time),
    FOREIGN KEY (template_id) REFERENCES coupon_template(id)
) ENGINE=InnoDB COMMENT '用户优惠券表';

-- 优惠券使用记录表（审计日志）
CREATE TABLE coupon_usage_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_coupon_id BIGINT NOT NULL COMMENT '用户优惠券ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    order_amount DECIMAL(10,2) NOT NULL COMMENT '订单原金额',
    discount_amount DECIMAL(10,2) NOT NULL COMMENT '优惠金额',
    final_amount DECIMAL(10,2) NOT NULL COMMENT '最终金额',
    action TINYINT NOT NULL COMMENT '操作: 1-使用 2-退还',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_order (order_id),
    INDEX idx_user (user_id),
    INDEX idx_user_coupon (user_coupon_id)
) ENGINE=InnoDB COMMENT '优惠券使用记录';

-- 示例数据
INSERT INTO coupon_template (name, type, discount_value, min_amount, max_discount, total_count, remain_count, start_time, end_time) VALUES
('新用户立减10元', 3, 10.00, 0, NULL, 1000, 950, '2024-01-01 00:00:00', '2025-12-31 23:59:59'),
('满100减20', 1, 20.00, 100.00, NULL, 500, 480, '2024-01-01 00:00:00', '2025-12-31 23:59:59'),
('全场9折', 2, 0.90, 50.00, 50.00, 200, 195, '2024-01-01 00:00:00', '2025-12-31 23:59:59');
