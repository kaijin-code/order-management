package com.example.coupon.grpc;

import com.example.coupon.grpc.*;
import com.example.coupon.service.CouponService;
import com.example.coupon.entity.CouponTemplate;
import com.example.coupon.entity.UserCoupon;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class CouponGrpcService extends CouponServiceGrpc.CouponServiceImplBase {

    @Autowired
    private CouponService couponService;

    @Override
    public void listAvailableTemplates(ListTemplatesRequest request,
                                       StreamObserver<ListTemplatesResponse> responseObserver) {
        int page = request.getPage() > 0 ? request.getPage() : 1;
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;

        List<CouponTemplate> templates = couponService.listAvailableTemplates(page, pageSize);
        int total = couponService.countAvailableTemplates();

        ListTemplatesResponse.Builder responseBuilder = ListTemplatesResponse.newBuilder()
                .setTotal(total);

        for (CouponTemplate template : templates) {
            responseBuilder.addTemplates(convertToProto(template));
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void claimCoupon(ClaimCouponRequest request,
                            StreamObserver<ClaimCouponResponse> responseObserver) {
        try {
            UserCoupon coupon = couponService.claimCoupon(
                    request.getUserId(),
                    request.getTemplateId()
            );

            ClaimCouponResponse response = ClaimCouponResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("领取成功")
                    .setCoupon(convertToProto(coupon))
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            ClaimCouponResponse response = ClaimCouponResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getUserCoupons(GetUserCouponsRequest request,
                               StreamObserver<GetUserCouponsResponse> responseObserver) {
        List<UserCoupon> coupons = couponService.getUserCoupons(
                request.getUserId(),
                request.getStatus()
        );

        GetUserCouponsResponse.Builder responseBuilder = GetUserCouponsResponse.newBuilder();
        for (UserCoupon coupon : coupons) {
            responseBuilder.addCoupons(convertToProto(coupon));
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void validateCoupon(ValidateCouponRequest request,
                               StreamObserver<ValidateCouponResponse> responseObserver) {
        try {
            var result = couponService.validateCoupon(
                    request.getCouponCode(),
                    request.getUserId(),
                    request.getOrderAmount()
            );

            ValidateCouponResponse response = ValidateCouponResponse.newBuilder()
                    .setValid(true)
                    .setMessage("优惠券可用")
                    .setDiscountAmount(result.getDiscountAmount())
                    .setFinalAmount(result.getFinalAmount())
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            ValidateCouponResponse response = ValidateCouponResponse.newBuilder()
                    .setValid(false)
                    .setMessage(e.getMessage())
                    .setDiscountAmount(0)
                    .setFinalAmount(request.getOrderAmount())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void useCoupon(UseCouponRequest request,
                          StreamObserver<UseCouponResponse> responseObserver) {
        try {
            var result = couponService.useCoupon(
                    request.getCouponCode(),
                    request.getUserId(),
                    request.getOrderId(),
                    request.getOrderAmount()
            );

            UseCouponResponse response = UseCouponResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("优惠券使用成功")
                    .setDiscountAmount(result.getDiscountAmount())
                    .setFinalAmount(result.getFinalAmount())
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            UseCouponResponse response = UseCouponResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void refundCoupon(RefundCouponRequest request,
                             StreamObserver<RefundCouponResponse> responseObserver) {
        try {
            couponService.refundCoupon(request.getOrderId(), request.getUserId());

            RefundCouponResponse response = RefundCouponResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("优惠券已退还")
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            RefundCouponResponse response = RefundCouponResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    // 实体转 Proto 对象
    private com.example.coupon.grpc.CouponTemplate convertToProto(CouponTemplate entity) {
        return com.example.coupon.grpc.CouponTemplate.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setType(CouponType.forNumber(entity.getType()))
                .setDiscountValue(entity.getDiscountValue().doubleValue())
                .setMinAmount(entity.getMinAmount().doubleValue())
                .setMaxDiscount(entity.getMaxDiscount() != null ? entity.getMaxDiscount().doubleValue() : 0)
                .setRemainCount(entity.getRemainCount())
                .setStartTime(entity.getStartTime().getTime())
                .setEndTime(entity.getEndTime().getTime())
                .build();
    }

    private com.example.coupon.grpc.UserCoupon convertToProto(UserCoupon entity) {
        return com.example.coupon.grpc.UserCoupon.newBuilder()
                .setId(entity.getId())
                .setCouponCode(entity.getCouponCode())
                .setTemplate(convertToProto(entity.getTemplate()))
                .setStatus(CouponStatus.forNumber(entity.getStatus()))
                .setExpireTime(entity.getExpireTime().getTime())
                .setCreatedAt(entity.getCreatedAt().getTime())
                .build();
    }
}
