import * as grpc from '@grpc/grpc-js';
import * as protoLoader from '@grpc/proto-loader';
import path from 'path';

const PROTO_PATH = path.join(process.cwd(), 'src/grpc/protos/coupon.proto');

// gRPC 服务地址配置
const COUPON_SERVICE_URL = process.env.COUPON_SERVICE_URL || 'localhost:9090';

// 加载 proto 文件
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
});

const couponProto = grpc.loadPackageDefinition(packageDefinition).coupon as any;

// 创建 gRPC 客户端（单例）
let couponClient: any = null;

export function getCouponClient() {
  if (!couponClient) {
    couponClient = new couponProto.CouponService(
      COUPON_SERVICE_URL,
      grpc.credentials.createInsecure() // 生产环境应使用 SSL
    );
  }
  return couponClient;
}

// 封装为 Promise 的辅助函数
function promisify<T>(client: any, method: string, request: any): Promise<T> {
  return new Promise((resolve, reject) => {
    client[method](request, (error: grpc.ServiceError | null, response: T) => {
      if (error) {
        reject(error);
      } else {
        resolve(response);
      }
    });
  });
}

// 优惠券服务 API 封装
export const couponService = {
  // 获取可用优惠券模板
  async listAvailableTemplates(page = 1, pageSize = 10) {
    const client = getCouponClient();
    return promisify(client, 'listAvailableTemplates', { page, page_size: pageSize });
  },

  // 用户领取优惠券
  async claimCoupon(userId: number, templateId: number) {
    const client = getCouponClient();
    return promisify(client, 'claimCoupon', { user_id: userId, template_id: templateId });
  },

  // 获取用户优惠券列表
  async getUserCoupons(userId: number, status?: string) {
    const client = getCouponClient();
    return promisify(client, 'getUserCoupons', { user_id: userId, status });
  },

  // 验证优惠券
  async validateCoupon(couponCode: string, userId: number, orderAmount: number) {
    const client = getCouponClient();
    return promisify(client, 'validateCoupon', {
      coupon_code: couponCode,
      user_id: userId,
      order_amount: orderAmount,
    });
  },

  // 使用优惠券
  async useCoupon(couponCode: string, userId: number, orderId: number, orderAmount: number) {
    const client = getCouponClient();
    return promisify(client, 'useCoupon', {
      coupon_code: couponCode,
      user_id: userId,
      order_id: orderId,
      order_amount: orderAmount,
    });
  },

  // 退还优惠券
  async refundCoupon(orderId: number, userId: number) {
    const client = getCouponClient();
    return promisify(client, 'refundCoupon', { order_id: orderId, user_id: userId });
  },
};
