package com.example.coupon.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.coupon.entity.CouponUsageLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponUsageLogRepository extends BaseMapper<CouponUsageLog> {
}
