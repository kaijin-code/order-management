package com.example.coupon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_template")
public class CouponTemplate {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 类型: 1-满减 2-折扣 3-立减
     */
    private Integer type;

    /**
     * 优惠值(满减金额/折扣比例/立减金额)
     */
    private BigDecimal discountValue;

    /**
     * 最低消费金额
     */
    private BigDecimal minAmount;

    /**
     * 最大优惠金额(折扣券用)
     */
    private BigDecimal maxDiscount;

    /**
     * 发放总量
     */
    private Integer totalCount;

    /**
     * 剩余数量
     */
    private Integer remainCount;

    /**
     * 生效开始时间
     */
    private LocalDateTime startTime;

    /**
     * 生效结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态: 0-禁用 1-启用
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
