package com.example.coupon.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.coupon.entity.CouponTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CouponTemplateRepository extends BaseMapper<CouponTemplate> {

    /**
     * 扣减库存（乐观锁）
     */
    @Update("UPDATE coupon_template SET remain_count = remain_count - 1 " +
            "WHERE id = #{templateId} AND remain_count > 0")
    int decrementRemainCount(@Param("templateId") Long templateId);
}
