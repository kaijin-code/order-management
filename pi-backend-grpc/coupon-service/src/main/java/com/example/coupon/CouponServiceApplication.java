package com.example.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.coupon.repository")
public class CouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class, args);
    }
}
