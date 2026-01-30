package com.example.coupon.service;

import com.example.coupon.entity.CouponTemplate;
import com.example.coupon.entity.UserCoupon;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {

    /**
     * 新用户注册送优惠券
     */
    List<UserCoupon> grantNewUserCoupons(Long userId);

    /**
     * 获取用户可用优惠券列表（按优惠金额排序）
     */
    List<UserCoupon> getAvailableCoupons(Long userId, BigDecimal orderAmount);

    /**
     * 使用优惠券
     */
    UseCouponResult useCoupon(Long userId, String couponCode, Long orderId, BigDecimal orderAmount);

    /**
     * 使用优惠券（Redisson分布式锁版本）
     */
    UseCouponResult useCouponWithRedisson(Long userId, String couponCode, Long orderId, BigDecimal orderAmount);

    /**
     * 验证优惠券是否可用
     */
    ValidateCouponResult validateCoupon(Long userId, String couponCode, BigDecimal orderAmount);

    /**
     * 创建优惠券模板
     */
    CouponTemplate createCouponTemplate(CouponTemplate template);

    /**
     * 领取优惠券
     */
    UserCoupon claimCoupon(Long userId, Long templateId);

    /**
     * 计算优惠金额
     */
    BigDecimal calculateDiscount(CouponTemplate template, BigDecimal orderAmount);

    /**
     * 使用优惠券结果
     */
    record UseCouponResult(boolean success, String message, BigDecimal discountAmount, BigDecimal finalAmount) {}

    /**
     * 验证优惠券结果
     */
    record ValidateCouponResult(boolean valid, String message, BigDecimal discountAmount) {}
}
