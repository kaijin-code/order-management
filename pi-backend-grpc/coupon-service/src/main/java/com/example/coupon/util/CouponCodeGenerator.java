package com.example.coupon.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CouponCodeGenerator {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * 生成优惠券码
     * 格式: 日期(6位) + 随机字符(10位)
     */
    public static String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().format(FORMATTER));
        for (int i = 0; i < 10; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 生成带前缀的优惠券码
     */
    public static String generate(String prefix) {
        return prefix + generate();
    }
}
