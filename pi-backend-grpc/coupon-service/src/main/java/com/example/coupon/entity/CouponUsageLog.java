package com.example.coupon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_usage_log")
public class CouponUsageLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userCouponId;

    private Long orderId;

    /**
     * 订单原金额
     */
    private BigDecimal orderAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 最终金额
     */
    private BigDecimal finalAmount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
