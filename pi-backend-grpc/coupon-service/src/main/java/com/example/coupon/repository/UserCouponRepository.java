package com.example.coupon.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.coupon.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCouponRepository extends BaseMapper<UserCoupon> {

    /**
     * 查询用户可用优惠券（关联模板信息）
     */
    @Select("SELECT uc.*, ct.name as template_name, ct.type, ct.discount_value, " +
            "ct.min_amount, ct.max_discount " +
            "FROM user_coupon uc " +
            "JOIN coupon_template ct ON uc.template_id = ct.id " +
            "WHERE uc.user_id = #{userId} AND uc.status = 0 " +
            "AND uc.expire_time > NOW() AND ct.status = 1")
    List<UserCoupon> findAvailableCoupons(@Param("userId") Long userId);

    /**
     * 根据优惠券码查询
     */
    @Select("SELECT * FROM user_coupon WHERE coupon_code = #{couponCode}")
    UserCoupon findByCouponCode(@Param("couponCode") String couponCode);
}
