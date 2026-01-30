package com.example.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.coupon.entity.CouponTemplate;
import com.example.coupon.entity.CouponUsageLog;
import com.example.coupon.entity.UserCoupon;
import com.example.coupon.repository.CouponTemplateRepository;
import com.example.coupon.repository.CouponUsageLogRepository;
import com.example.coupon.repository.UserCouponRepository;
import com.example.coupon.service.CouponService;
import com.example.coupon.util.CouponCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponTemplateRepository templateRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponUsageLogRepository usageLogRepository;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    @Value("${coupon.new-user.template-ids:1,2,3}")
    private String newUserTemplateIds;

    private static final String LOCK_PREFIX = "coupon:lock:";
    private static final String NEW_USER_GRANT_KEY = "coupon:new_user:";


    @Override
    @Transactional
    public List<UserCoupon> grantNewUserCoupons(Long userId) {
        // 防止重复发放
        String key = NEW_USER_GRANT_KEY + userId;
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, "1", 30, TimeUnit.DAYS);
        if (Boolean.FALSE.equals(isNew)) {
            log.warn("用户 {} 已领取过新人优惠券", userId);
            return List.of();
        }

        List<UserCoupon> grantedCoupons = new ArrayList<>();
        String[] templateIds = newUserTemplateIds.split(",");

        for (String templateIdStr : templateIds) {
            try {
                Long templateId = Long.parseLong(templateIdStr.trim());
                UserCoupon coupon = claimCoupon(userId, templateId);
                if (coupon != null) {
                    grantedCoupons.add(coupon);
                }
            } catch (Exception e) {
                log.error("发放新人优惠券失败, templateId: {}", templateIdStr, e);
            }
        }

        log.info("用户 {} 成功领取 {} 张新人优惠券", userId, grantedCoupons.size());
        return grantedCoupons;
    }

    @Override
    public List<UserCoupon> getAvailableCoupons(Long userId, BigDecimal orderAmount) {
        // 查询用户所有未使用且未过期的优惠券
        LambdaQueryWrapper<UserCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus, 0)
                .gt(UserCoupon::getExpireTime, LocalDateTime.now());

        List<UserCoupon> userCoupons = userCouponRepository.selectList(wrapper);

        // 关联模板信息并筛选满足条件的优惠券
        List<UserCoupon> availableCoupons = new ArrayList<>();
        for (UserCoupon coupon : userCoupons) {
            CouponTemplate template = templateRepository.selectById(coupon.getTemplateId());
            if (template != null && template.getStatus() == 1) {
                // 检查是否满足最低消费
                if (orderAmount.compareTo(template.getMinAmount()) >= 0) {
                    coupon.setTemplate(template);
                    availableCoupons.add(coupon);
                }
            }
        }

        // 按优惠金额从高到低排序
        availableCoupons.sort((c1, c2) -> {
            BigDecimal discount1 = calculateDiscount(c1.getTemplate(), orderAmount);
            BigDecimal discount2 = calculateDiscount(c2.getTemplate(), orderAmount);
            return discount2.compareTo(discount1);
        });

        return availableCoupons;
    }


    @Override
    @Transactional
    public UseCouponResult useCoupon(Long userId, String couponCode, Long orderId, BigDecimal orderAmount) {
        // 分布式锁防止并发使用
        String lockKey = LOCK_PREFIX + couponCode;
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(locked)) {
            return new UseCouponResult(false, "优惠券正在被使用，请稍后重试", BigDecimal.ZERO, orderAmount);
        }

        try {
            // 验证优惠券
            ValidateCouponResult validateResult = validateCoupon(userId, couponCode, orderAmount);
            if (!validateResult.valid()) {
                return new UseCouponResult(false, validateResult.message(), BigDecimal.ZERO, orderAmount);
            }

            // 更新优惠券状态
            UserCoupon coupon = userCouponRepository.findByCouponCode(couponCode);
            LambdaUpdateWrapper<UserCoupon> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserCoupon::getId, coupon.getId())
                    .eq(UserCoupon::getStatus, 0)
                    .set(UserCoupon::getStatus, 1)
                    .set(UserCoupon::getUsedOrderId, orderId)
                    .set(UserCoupon::getUsedAt, LocalDateTime.now());

            int updated = userCouponRepository.update(null, updateWrapper);
            if (updated == 0) {
                return new UseCouponResult(false, "优惠券状态已变更", BigDecimal.ZERO, orderAmount);
            }

            // 记录使用日志
            BigDecimal discountAmount = validateResult.discountAmount();
            BigDecimal finalAmount = orderAmount.subtract(discountAmount);
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            CouponUsageLog usageLog = new CouponUsageLog();
            usageLog.setUserCouponId(coupon.getId());
            usageLog.setOrderId(orderId);
            usageLog.setOrderAmount(orderAmount);
            usageLog.setDiscountAmount(discountAmount);
            usageLog.setFinalAmount(finalAmount);
            usageLogRepository.insert(usageLog);

            log.info("用户 {} 使用优惠券 {} 成功，订单 {}，优惠 {}", userId, couponCode, orderId, discountAmount);
            return new UseCouponResult(true, "优惠券使用成功", discountAmount, finalAmount);

        } finally {
            redisTemplate.delete(lockKey);
        }
    }

    @Override
    @Transactional
    public UseCouponResult useCouponWithRedisson(Long userId, String couponCode, Long orderId, BigDecimal orderAmount) {
        String lockKey = LOCK_PREFIX + couponCode;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试获取锁，等待3秒，锁自动续期（看门狗机制）
            boolean locked = lock.tryLock(3, TimeUnit.SECONDS);
            if (!locked) {
                return new UseCouponResult(false, "优惠券正在被使用，请稍后重试", BigDecimal.ZERO, orderAmount);
            }

            // 验证优惠券
            ValidateCouponResult validateResult = validateCoupon(userId, couponCode, orderAmount);
            if (!validateResult.valid()) {
                return new UseCouponResult(false, validateResult.message(), BigDecimal.ZERO, orderAmount);
            }

            // 更新优惠券状态
            UserCoupon coupon = userCouponRepository.findByCouponCode(couponCode);
            LambdaUpdateWrapper<UserCoupon> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserCoupon::getId, coupon.getId())
                    .eq(UserCoupon::getStatus, 0)
                    .set(UserCoupon::getStatus, 1)
                    .set(UserCoupon::getUsedOrderId, orderId)
                    .set(UserCoupon::getUsedAt, LocalDateTime.now());

            int updated = userCouponRepository.update(null, updateWrapper);
            if (updated == 0) {
                return new UseCouponResult(false, "优惠券状态已变更", BigDecimal.ZERO, orderAmount);
            }

            // 记录使用日志
            BigDecimal discountAmount = validateResult.discountAmount();
            BigDecimal finalAmount = orderAmount.subtract(discountAmount);
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            CouponUsageLog usageLog = new CouponUsageLog();
            usageLog.setUserCouponId(coupon.getId());
            usageLog.setOrderId(orderId);
            usageLog.setOrderAmount(orderAmount);
            usageLog.setDiscountAmount(discountAmount);
            usageLog.setFinalAmount(finalAmount);
            usageLogRepository.insert(usageLog);

            log.info("用户 {} 使用优惠券 {} 成功（Redisson锁），订单 {}，优惠 {}", userId, couponCode, orderId, discountAmount);
            return new UseCouponResult(true, "优惠券使用成功", discountAmount, finalAmount);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new UseCouponResult(false, "获取锁被中断", BigDecimal.ZERO, orderAmount);
        } finally {
            // Redisson会自动判断是否是当前线程持有的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public ValidateCouponResult validateCoupon(Long userId, String couponCode, BigDecimal orderAmount) {
        UserCoupon coupon = userCouponRepository.findByCouponCode(couponCode);
        if (coupon == null) {
            return new ValidateCouponResult(false, "优惠券不存在", BigDecimal.ZERO);
        }

        if (!coupon.getUserId().equals(userId)) {
            return new ValidateCouponResult(false, "优惠券不属于当前用户", BigDecimal.ZERO);
        }

        if (coupon.getStatus() != 0) {
            String statusMsg = coupon.getStatus() == 1 ? "已使用" : "已过期";
            return new ValidateCouponResult(false, "优惠券" + statusMsg, BigDecimal.ZERO);
        }

        if (coupon.getExpireTime().isBefore(LocalDateTime.now())) {
            return new ValidateCouponResult(false, "优惠券已过期", BigDecimal.ZERO);
        }

        CouponTemplate template = templateRepository.selectById(coupon.getTemplateId());
        if (template == null || template.getStatus() != 1) {
            return new ValidateCouponResult(false, "优惠券模板无效", BigDecimal.ZERO);
        }

        if (orderAmount.compareTo(template.getMinAmount()) < 0) {
            return new ValidateCouponResult(false, 
                    "订单金额未满足最低消费 " + template.getMinAmount() + " 元", BigDecimal.ZERO);
        }

        BigDecimal discountAmount = calculateDiscount(template, orderAmount);
        return new ValidateCouponResult(true, "验证通过", discountAmount);
    }


    @Override
    @Transactional
    public CouponTemplate createCouponTemplate(CouponTemplate template) {
        template.setRemainCount(template.getTotalCount());
        template.setStatus(1);
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        templateRepository.insert(template);
        log.info("创建优惠券模板成功: {}", template.getId());
        return template;
    }

    @Override
    @Transactional
    public UserCoupon claimCoupon(Long userId, Long templateId) {
        CouponTemplate template = templateRepository.selectById(templateId);
        if (template == null) {
            throw new RuntimeException("优惠券模板不存在");
        }

        if (template.getStatus() != 1) {
            throw new RuntimeException("优惠券模板已禁用");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(template.getStartTime()) || now.isAfter(template.getEndTime())) {
            throw new RuntimeException("优惠券不在有效期内");
        }

        // 扣减库存
        int updated = templateRepository.decrementRemainCount(templateId);
        if (updated == 0) {
            throw new RuntimeException("优惠券已领完");
        }

        // 创建用户优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setTemplateId(templateId);
        userCoupon.setCouponCode(CouponCodeGenerator.generate());
        userCoupon.setStatus(0);
        userCoupon.setExpireTime(template.getEndTime());
        userCoupon.setCreatedAt(LocalDateTime.now());
        userCouponRepository.insert(userCoupon);

        userCoupon.setTemplate(template);
        log.info("用户 {} 领取优惠券 {} 成功", userId, userCoupon.getCouponCode());
        return userCoupon;
    }

    @Override
    public BigDecimal calculateDiscount(CouponTemplate template, BigDecimal orderAmount) {
        if (template == null || orderAmount == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount;
        switch (template.getType()) {
            case 1: // 满减
                discount = template.getDiscountValue();
                break;
            case 2: // 折扣
                // discountValue 是折扣比例，如 0.9 表示9折
                BigDecimal discountRate = BigDecimal.ONE.subtract(template.getDiscountValue());
                discount = orderAmount.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                // 限制最大优惠金额
                if (template.getMaxDiscount() != null && discount.compareTo(template.getMaxDiscount()) > 0) {
                    discount = template.getMaxDiscount();
                }
                break;
            case 3: // 立减
                discount = template.getDiscountValue();
                break;
            default:
                discount = BigDecimal.ZERO;
        }

        // 优惠金额不能超过订单金额
        if (discount.compareTo(orderAmount) > 0) {
            discount = orderAmount;
        }

        return discount;
    }
}
