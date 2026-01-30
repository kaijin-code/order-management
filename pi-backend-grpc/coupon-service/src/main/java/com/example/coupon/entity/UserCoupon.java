package com.example.coupon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCoupon {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long templateId;

    private String couponCode;

    /**
     * 状态: 0-未使用 1-已使用 2-已过期
     */
    private Integer status;

    /**
     * 使用的订单ID
     */
    private Long usedOrderId;

    /**
     * 使用时间
     */
    private LocalDateTime usedAt;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 关联的优惠券模板（非数据库字段）
     */
    @TableField(exist = false)
    private CouponTemplate template;
}
