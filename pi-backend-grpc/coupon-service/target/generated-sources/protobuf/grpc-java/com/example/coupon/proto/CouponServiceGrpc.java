package com.example.coupon.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 优惠券服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: coupon.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CouponServiceGrpc {

  private CouponServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "coupon.CouponService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.GrantNewUserCouponsRequest,
      com.example.coupon.proto.GrantNewUserCouponsResponse> getGrantNewUserCouponsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GrantNewUserCoupons",
      requestType = com.example.coupon.proto.GrantNewUserCouponsRequest.class,
      responseType = com.example.coupon.proto.GrantNewUserCouponsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.GrantNewUserCouponsRequest,
      com.example.coupon.proto.GrantNewUserCouponsResponse> getGrantNewUserCouponsMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.GrantNewUserCouponsRequest, com.example.coupon.proto.GrantNewUserCouponsResponse> getGrantNewUserCouponsMethod;
    if ((getGrantNewUserCouponsMethod = CouponServiceGrpc.getGrantNewUserCouponsMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getGrantNewUserCouponsMethod = CouponServiceGrpc.getGrantNewUserCouponsMethod) == null) {
          CouponServiceGrpc.getGrantNewUserCouponsMethod = getGrantNewUserCouponsMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.GrantNewUserCouponsRequest, com.example.coupon.proto.GrantNewUserCouponsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GrantNewUserCoupons"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.GrantNewUserCouponsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.GrantNewUserCouponsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("GrantNewUserCoupons"))
              .build();
        }
      }
    }
    return getGrantNewUserCouponsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.GetAvailableCouponsRequest,
      com.example.coupon.proto.GetAvailableCouponsResponse> getGetAvailableCouponsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAvailableCoupons",
      requestType = com.example.coupon.proto.GetAvailableCouponsRequest.class,
      responseType = com.example.coupon.proto.GetAvailableCouponsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.GetAvailableCouponsRequest,
      com.example.coupon.proto.GetAvailableCouponsResponse> getGetAvailableCouponsMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.GetAvailableCouponsRequest, com.example.coupon.proto.GetAvailableCouponsResponse> getGetAvailableCouponsMethod;
    if ((getGetAvailableCouponsMethod = CouponServiceGrpc.getGetAvailableCouponsMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getGetAvailableCouponsMethod = CouponServiceGrpc.getGetAvailableCouponsMethod) == null) {
          CouponServiceGrpc.getGetAvailableCouponsMethod = getGetAvailableCouponsMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.GetAvailableCouponsRequest, com.example.coupon.proto.GetAvailableCouponsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAvailableCoupons"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.GetAvailableCouponsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.GetAvailableCouponsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("GetAvailableCoupons"))
              .build();
        }
      }
    }
    return getGetAvailableCouponsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.UseCouponRequest,
      com.example.coupon.proto.UseCouponResponse> getUseCouponMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UseCoupon",
      requestType = com.example.coupon.proto.UseCouponRequest.class,
      responseType = com.example.coupon.proto.UseCouponResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.UseCouponRequest,
      com.example.coupon.proto.UseCouponResponse> getUseCouponMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.UseCouponRequest, com.example.coupon.proto.UseCouponResponse> getUseCouponMethod;
    if ((getUseCouponMethod = CouponServiceGrpc.getUseCouponMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getUseCouponMethod = CouponServiceGrpc.getUseCouponMethod) == null) {
          CouponServiceGrpc.getUseCouponMethod = getUseCouponMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.UseCouponRequest, com.example.coupon.proto.UseCouponResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UseCoupon"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.UseCouponRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.UseCouponResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("UseCoupon"))
              .build();
        }
      }
    }
    return getUseCouponMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.ValidateCouponRequest,
      com.example.coupon.proto.ValidateCouponResponse> getValidateCouponMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateCoupon",
      requestType = com.example.coupon.proto.ValidateCouponRequest.class,
      responseType = com.example.coupon.proto.ValidateCouponResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.ValidateCouponRequest,
      com.example.coupon.proto.ValidateCouponResponse> getValidateCouponMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.ValidateCouponRequest, com.example.coupon.proto.ValidateCouponResponse> getValidateCouponMethod;
    if ((getValidateCouponMethod = CouponServiceGrpc.getValidateCouponMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getValidateCouponMethod = CouponServiceGrpc.getValidateCouponMethod) == null) {
          CouponServiceGrpc.getValidateCouponMethod = getValidateCouponMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.ValidateCouponRequest, com.example.coupon.proto.ValidateCouponResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidateCoupon"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.ValidateCouponRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.ValidateCouponResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("ValidateCoupon"))
              .build();
        }
      }
    }
    return getValidateCouponMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.CreateCouponTemplateRequest,
      com.example.coupon.proto.CreateCouponTemplateResponse> getCreateCouponTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateCouponTemplate",
      requestType = com.example.coupon.proto.CreateCouponTemplateRequest.class,
      responseType = com.example.coupon.proto.CreateCouponTemplateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.CreateCouponTemplateRequest,
      com.example.coupon.proto.CreateCouponTemplateResponse> getCreateCouponTemplateMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.CreateCouponTemplateRequest, com.example.coupon.proto.CreateCouponTemplateResponse> getCreateCouponTemplateMethod;
    if ((getCreateCouponTemplateMethod = CouponServiceGrpc.getCreateCouponTemplateMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getCreateCouponTemplateMethod = CouponServiceGrpc.getCreateCouponTemplateMethod) == null) {
          CouponServiceGrpc.getCreateCouponTemplateMethod = getCreateCouponTemplateMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.CreateCouponTemplateRequest, com.example.coupon.proto.CreateCouponTemplateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateCouponTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.CreateCouponTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.CreateCouponTemplateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("CreateCouponTemplate"))
              .build();
        }
      }
    }
    return getCreateCouponTemplateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.coupon.proto.ClaimCouponRequest,
      com.example.coupon.proto.ClaimCouponResponse> getClaimCouponMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClaimCoupon",
      requestType = com.example.coupon.proto.ClaimCouponRequest.class,
      responseType = com.example.coupon.proto.ClaimCouponResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.coupon.proto.ClaimCouponRequest,
      com.example.coupon.proto.ClaimCouponResponse> getClaimCouponMethod() {
    io.grpc.MethodDescriptor<com.example.coupon.proto.ClaimCouponRequest, com.example.coupon.proto.ClaimCouponResponse> getClaimCouponMethod;
    if ((getClaimCouponMethod = CouponServiceGrpc.getClaimCouponMethod) == null) {
      synchronized (CouponServiceGrpc.class) {
        if ((getClaimCouponMethod = CouponServiceGrpc.getClaimCouponMethod) == null) {
          CouponServiceGrpc.getClaimCouponMethod = getClaimCouponMethod =
              io.grpc.MethodDescriptor.<com.example.coupon.proto.ClaimCouponRequest, com.example.coupon.proto.ClaimCouponResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClaimCoupon"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.ClaimCouponRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.coupon.proto.ClaimCouponResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CouponServiceMethodDescriptorSupplier("ClaimCoupon"))
              .build();
        }
      }
    }
    return getClaimCouponMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CouponServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CouponServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CouponServiceStub>() {
        @java.lang.Override
        public CouponServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CouponServiceStub(channel, callOptions);
        }
      };
    return CouponServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CouponServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CouponServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CouponServiceBlockingStub>() {
        @java.lang.Override
        public CouponServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CouponServiceBlockingStub(channel, callOptions);
        }
      };
    return CouponServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CouponServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CouponServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CouponServiceFutureStub>() {
        @java.lang.Override
        public CouponServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CouponServiceFutureStub(channel, callOptions);
        }
      };
    return CouponServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 优惠券服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 新用户注册送优惠券
     * </pre>
     */
    default void grantNewUserCoupons(com.example.coupon.proto.GrantNewUserCouponsRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.GrantNewUserCouponsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGrantNewUserCouponsMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取用户可用优惠券列表（按优惠金额排序）
     * </pre>
     */
    default void getAvailableCoupons(com.example.coupon.proto.GetAvailableCouponsRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.GetAvailableCouponsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAvailableCouponsMethod(), responseObserver);
    }

    /**
     * <pre>
     * 使用优惠券
     * </pre>
     */
    default void useCoupon(com.example.coupon.proto.UseCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.UseCouponResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUseCouponMethod(), responseObserver);
    }

    /**
     * <pre>
     * 验证优惠券是否可用
     * </pre>
     */
    default void validateCoupon(com.example.coupon.proto.ValidateCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.ValidateCouponResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateCouponMethod(), responseObserver);
    }

    /**
     * <pre>
     * 创建优惠券模板
     * </pre>
     */
    default void createCouponTemplate(com.example.coupon.proto.CreateCouponTemplateRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.CreateCouponTemplateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateCouponTemplateMethod(), responseObserver);
    }

    /**
     * <pre>
     * 领取优惠券
     * </pre>
     */
    default void claimCoupon(com.example.coupon.proto.ClaimCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.ClaimCouponResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getClaimCouponMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CouponService.
   * <pre>
   * 优惠券服务
   * </pre>
   */
  public static abstract class CouponServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CouponServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CouponService.
   * <pre>
   * 优惠券服务
   * </pre>
   */
  public static final class CouponServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CouponServiceStub> {
    private CouponServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CouponServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CouponServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 新用户注册送优惠券
     * </pre>
     */
    public void grantNewUserCoupons(com.example.coupon.proto.GrantNewUserCouponsRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.GrantNewUserCouponsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGrantNewUserCouponsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取用户可用优惠券列表（按优惠金额排序）
     * </pre>
     */
    public void getAvailableCoupons(com.example.coupon.proto.GetAvailableCouponsRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.GetAvailableCouponsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAvailableCouponsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 使用优惠券
     * </pre>
     */
    public void useCoupon(com.example.coupon.proto.UseCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.UseCouponResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUseCouponMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 验证优惠券是否可用
     * </pre>
     */
    public void validateCoupon(com.example.coupon.proto.ValidateCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.ValidateCouponResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateCouponMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 创建优惠券模板
     * </pre>
     */
    public void createCouponTemplate(com.example.coupon.proto.CreateCouponTemplateRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.CreateCouponTemplateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateCouponTemplateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 领取优惠券
     * </pre>
     */
    public void claimCoupon(com.example.coupon.proto.ClaimCouponRequest request,
        io.grpc.stub.StreamObserver<com.example.coupon.proto.ClaimCouponResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getClaimCouponMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CouponService.
   * <pre>
   * 优惠券服务
   * </pre>
   */
  public static final class CouponServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CouponServiceBlockingStub> {
    private CouponServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CouponServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CouponServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 新用户注册送优惠券
     * </pre>
     */
    public com.example.coupon.proto.GrantNewUserCouponsResponse grantNewUserCoupons(com.example.coupon.proto.GrantNewUserCouponsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGrantNewUserCouponsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取用户可用优惠券列表（按优惠金额排序）
     * </pre>
     */
    public com.example.coupon.proto.GetAvailableCouponsResponse getAvailableCoupons(com.example.coupon.proto.GetAvailableCouponsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAvailableCouponsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 使用优惠券
     * </pre>
     */
    public com.example.coupon.proto.UseCouponResponse useCoupon(com.example.coupon.proto.UseCouponRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUseCouponMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 验证优惠券是否可用
     * </pre>
     */
    public com.example.coupon.proto.ValidateCouponResponse validateCoupon(com.example.coupon.proto.ValidateCouponRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateCouponMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 创建优惠券模板
     * </pre>
     */
    public com.example.coupon.proto.CreateCouponTemplateResponse createCouponTemplate(com.example.coupon.proto.CreateCouponTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCouponTemplateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 领取优惠券
     * </pre>
     */
    public com.example.coupon.proto.ClaimCouponResponse claimCoupon(com.example.coupon.proto.ClaimCouponRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getClaimCouponMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CouponService.
   * <pre>
   * 优惠券服务
   * </pre>
   */
  public static final class CouponServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CouponServiceFutureStub> {
    private CouponServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CouponServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CouponServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 新用户注册送优惠券
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.GrantNewUserCouponsResponse> grantNewUserCoupons(
        com.example.coupon.proto.GrantNewUserCouponsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGrantNewUserCouponsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取用户可用优惠券列表（按优惠金额排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.GetAvailableCouponsResponse> getAvailableCoupons(
        com.example.coupon.proto.GetAvailableCouponsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAvailableCouponsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 使用优惠券
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.UseCouponResponse> useCoupon(
        com.example.coupon.proto.UseCouponRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUseCouponMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 验证优惠券是否可用
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.ValidateCouponResponse> validateCoupon(
        com.example.coupon.proto.ValidateCouponRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateCouponMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 创建优惠券模板
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.CreateCouponTemplateResponse> createCouponTemplate(
        com.example.coupon.proto.CreateCouponTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateCouponTemplateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 领取优惠券
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.coupon.proto.ClaimCouponResponse> claimCoupon(
        com.example.coupon.proto.ClaimCouponRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getClaimCouponMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GRANT_NEW_USER_COUPONS = 0;
  private static final int METHODID_GET_AVAILABLE_COUPONS = 1;
  private static final int METHODID_USE_COUPON = 2;
  private static final int METHODID_VALIDATE_COUPON = 3;
  private static final int METHODID_CREATE_COUPON_TEMPLATE = 4;
  private static final int METHODID_CLAIM_COUPON = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GRANT_NEW_USER_COUPONS:
          serviceImpl.grantNewUserCoupons((com.example.coupon.proto.GrantNewUserCouponsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.GrantNewUserCouponsResponse>) responseObserver);
          break;
        case METHODID_GET_AVAILABLE_COUPONS:
          serviceImpl.getAvailableCoupons((com.example.coupon.proto.GetAvailableCouponsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.GetAvailableCouponsResponse>) responseObserver);
          break;
        case METHODID_USE_COUPON:
          serviceImpl.useCoupon((com.example.coupon.proto.UseCouponRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.UseCouponResponse>) responseObserver);
          break;
        case METHODID_VALIDATE_COUPON:
          serviceImpl.validateCoupon((com.example.coupon.proto.ValidateCouponRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.ValidateCouponResponse>) responseObserver);
          break;
        case METHODID_CREATE_COUPON_TEMPLATE:
          serviceImpl.createCouponTemplate((com.example.coupon.proto.CreateCouponTemplateRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.CreateCouponTemplateResponse>) responseObserver);
          break;
        case METHODID_CLAIM_COUPON:
          serviceImpl.claimCoupon((com.example.coupon.proto.ClaimCouponRequest) request,
              (io.grpc.stub.StreamObserver<com.example.coupon.proto.ClaimCouponResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGrantNewUserCouponsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.GrantNewUserCouponsRequest,
              com.example.coupon.proto.GrantNewUserCouponsResponse>(
                service, METHODID_GRANT_NEW_USER_COUPONS)))
        .addMethod(
          getGetAvailableCouponsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.GetAvailableCouponsRequest,
              com.example.coupon.proto.GetAvailableCouponsResponse>(
                service, METHODID_GET_AVAILABLE_COUPONS)))
        .addMethod(
          getUseCouponMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.UseCouponRequest,
              com.example.coupon.proto.UseCouponResponse>(
                service, METHODID_USE_COUPON)))
        .addMethod(
          getValidateCouponMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.ValidateCouponRequest,
              com.example.coupon.proto.ValidateCouponResponse>(
                service, METHODID_VALIDATE_COUPON)))
        .addMethod(
          getCreateCouponTemplateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.CreateCouponTemplateRequest,
              com.example.coupon.proto.CreateCouponTemplateResponse>(
                service, METHODID_CREATE_COUPON_TEMPLATE)))
        .addMethod(
          getClaimCouponMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.coupon.proto.ClaimCouponRequest,
              com.example.coupon.proto.ClaimCouponResponse>(
                service, METHODID_CLAIM_COUPON)))
        .build();
  }

  private static abstract class CouponServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CouponServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.coupon.proto.CouponProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CouponService");
    }
  }

  private static final class CouponServiceFileDescriptorSupplier
      extends CouponServiceBaseDescriptorSupplier {
    CouponServiceFileDescriptorSupplier() {}
  }

  private static final class CouponServiceMethodDescriptorSupplier
      extends CouponServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CouponServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CouponServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CouponServiceFileDescriptorSupplier())
              .addMethod(getGrantNewUserCouponsMethod())
              .addMethod(getGetAvailableCouponsMethod())
              .addMethod(getUseCouponMethod())
              .addMethod(getValidateCouponMethod())
              .addMethod(getCreateCouponTemplateMethod())
              .addMethod(getClaimCouponMethod())
              .build();
        }
      }
    }
    return result;
  }
}
