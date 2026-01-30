package com.example.coupon.grpc;

import com.example.coupon.entity.CouponTemplate;
import com.example.coupon.entity.UserCoupon;
import com.example.coupon.proto.*;
import com.example.coupon.service.CouponService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class CouponGrpcService extends CouponServiceGrpc.CouponServiceImplBase {

    private final CouponService couponService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void grantNewUserCoupons(GrantNewUserCouponsRequest request,
                                    StreamObserver<GrantNewUserCouponsResponse> responseObserver) {
        try {
            List<UserCoupon> coupons = couponService.grantNewUserCoupons(request.getUserId());
            
            GrantNewUserCouponsResponse.Builder builder = GrantNewUserCouponsResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("新人优惠券发放成功");

            for (UserCoupon coupon : coupons) {
                builder.addCoupons(convertToCouponInfo(coupon, BigDecimal.ZERO));
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("发放新人优惠券失败", e);
            responseObserver.onNext(GrantNewUserCouponsResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }


    @Override
    public void getAvailableCoupons(GetAvailableCouponsRequest request,
                                    StreamObserver<GetAvailableCouponsResponse> responseObserver) {
        try {
            BigDecimal orderAmount = BigDecimal.valueOf(request.getOrderAmount());
            List<UserCoupon> coupons = couponService.getAvailableCoupons(request.getUserId(), orderAmount);

            GetAvailableCouponsResponse.Builder builder = GetAvailableCouponsResponse.newBuilder();

            for (UserCoupon coupon : coupons) {
                BigDecimal discount = couponService.calculateDiscount(coupon.getTemplate(), orderAmount);
                builder.addCoupons(convertToCouponInfo(coupon, discount));
            }

            // 设置推荐优惠券（第一个即为优惠最高的）
            if (!coupons.isEmpty()) {
                UserCoupon recommended = coupons.get(0);
                BigDecimal discount = couponService.calculateDiscount(recommended.getTemplate(), orderAmount);
                builder.setRecommendedCoupon(convertToCouponInfo(recommended, discount));
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("获取可用优惠券失败", e);
            responseObserver.onNext(GetAvailableCouponsResponse.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void useCoupon(UseCouponRequest request, StreamObserver<UseCouponResponse> responseObserver) {
        try {
            CouponService.UseCouponResult result = couponService.useCouponWithRedisson(
                    request.getUserId(),
                    request.getCouponCode(),
                    request.getOrderId(),
                    BigDecimal.valueOf(request.getOrderAmount())
            );

            responseObserver.onNext(UseCouponResponse.newBuilder()
                    .setSuccess(result.success())
                    .setMessage(result.message())
                    .setDiscountAmount(result.discountAmount().doubleValue())
                    .setFinalAmount(result.finalAmount().doubleValue())
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("使用优惠券失败", e);
            responseObserver.onNext(UseCouponResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void validateCoupon(ValidateCouponRequest request,
                               StreamObserver<ValidateCouponResponse> responseObserver) {
        try {
            CouponService.ValidateCouponResult result = couponService.validateCoupon(
                    request.getUserId(),
                    request.getCouponCode(),
                    BigDecimal.valueOf(request.getOrderAmount())
            );

            responseObserver.onNext(ValidateCouponResponse.newBuilder()
                    .setValid(result.valid())
                    .setMessage(result.message())
                    .setDiscountAmount(result.discountAmount().doubleValue())
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("验证优惠券失败", e);
            responseObserver.onNext(ValidateCouponResponse.newBuilder()
                    .setValid(false)
                    .setMessage(e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }


    @Override
    public void createCouponTemplate(CreateCouponTemplateRequest request,
                                     StreamObserver<CreateCouponTemplateResponse> responseObserver) {
        try {
            CouponTemplate template = new CouponTemplate();
            template.setName(request.getName());
            template.setType(request.getTypeValue());
            template.setDiscountValue(BigDecimal.valueOf(request.getDiscountValue()));
            template.setMinAmount(BigDecimal.valueOf(request.getMinAmount()));
            template.setMaxDiscount(request.getMaxDiscount() > 0 ? 
                    BigDecimal.valueOf(request.getMaxDiscount()) : null);
            template.setTotalCount(request.getTotalCount());
            template.setStartTime(LocalDateTime.parse(request.getStartTime(), FORMATTER));
            template.setEndTime(LocalDateTime.parse(request.getEndTime(), FORMATTER));

            CouponTemplate created = couponService.createCouponTemplate(template);

            responseObserver.onNext(CreateCouponTemplateResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("优惠券模板创建成功")
                    .setTemplateId(created.getId())
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("创建优惠券模板失败", e);
            responseObserver.onNext(CreateCouponTemplateResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void claimCoupon(ClaimCouponRequest request, StreamObserver<ClaimCouponResponse> responseObserver) {
        try {
            UserCoupon coupon = couponService.claimCoupon(request.getUserId(), request.getTemplateId());

            responseObserver.onNext(ClaimCouponResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("优惠券领取成功")
                    .setCoupon(convertToCouponInfo(coupon, BigDecimal.ZERO))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("领取优惠券失败", e);
            responseObserver.onNext(ClaimCouponResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    private CouponInfo convertToCouponInfo(UserCoupon coupon, BigDecimal calculatedDiscount) {
        CouponTemplate template = coupon.getTemplate();
        CouponInfo.Builder builder = CouponInfo.newBuilder()
                .setId(coupon.getId())
                .setUserId(coupon.getUserId())
                .setTemplateId(coupon.getTemplateId())
                .setCouponCode(coupon.getCouponCode())
                .setStatus(convertStatus(coupon.getStatus()))
                .setExpireTime(coupon.getExpireTime().format(FORMATTER))
                .setCalculatedDiscount(calculatedDiscount.doubleValue());

        if (template != null) {
            builder.setName(template.getName())
                    .setType(CouponType.forNumber(template.getType()))
                    .setDiscountValue(template.getDiscountValue().doubleValue())
                    .setMinAmount(template.getMinAmount().doubleValue());
            if (template.getMaxDiscount() != null) {
                builder.setMaxDiscount(template.getMaxDiscount().doubleValue());
            }
        }

        return builder.build();
    }

    private CouponStatus convertStatus(Integer status) {
        return switch (status) {
            case 0 -> CouponStatus.UNUSED;
            case 1 -> CouponStatus.USED;
            case 2 -> CouponStatus.EXPIRED;
            default -> CouponStatus.COUPON_STATUS_UNSPECIFIED;
        };
    }
}
