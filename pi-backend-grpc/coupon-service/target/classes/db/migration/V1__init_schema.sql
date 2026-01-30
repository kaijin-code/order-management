-- 优惠券模板表（定义优惠券规则）
CREATE TABLE IF NOT EXISTS coupon_template (
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
CREATE TABLE IF NOT EXISTS user_coupon (
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
CREATE TABLE IF NOT EXISTS coupon_usage_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_coupon_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    order_amount DECIMAL(10,2) NOT NULL COMMENT '订单原金额',
    discount_amount DECIMAL(10,2) NOT NULL COMMENT '优惠金额',
    final_amount DECIMAL(10,2) NOT NULL COMMENT '最终金额',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_order (order_id)
) COMMENT '优惠券使用记录';

-- 插入示例优惠券模板（新用户专享）
INSERT INTO coupon_template (name, type, discount_value, min_amount, max_discount, total_count, remain_count, start_time, end_time, status) VALUES
('新人满100减20', 1, 20.00, 100.00, NULL, 10000, 10000, NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), 1),
('新人9折券', 2, 0.90, 50.00, 50.00, 10000, 10000, NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), 1),
('新人立减10元', 3, 10.00, 0.00, NULL, 10000, 10000, NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), 1);
