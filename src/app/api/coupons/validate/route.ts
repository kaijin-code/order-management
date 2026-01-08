import { NextResponse } from 'next/server';
import { verifyToken } from '@/lib/auth';
import { couponService } from '@/grpc/client';

function getUserFromRequest(request: Request) {
  const authHeader = request.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) return null;
  return verifyToken(authHeader.slice(7));
}

// 验证优惠券并计算优惠金额
export async function POST(request: Request) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  try {
    const { couponCode, orderAmount } = await request.json();
    if (!couponCode || !orderAmount) {
      return NextResponse.json({ error: '优惠券码和订单金额不能为空' }, { status: 400 });
    }

    const result = await couponService.validateCoupon(couponCode, user.userId, orderAmount);
    return NextResponse.json(result);
  } catch (error) {
    console.error('验证优惠券失败:', error);
    return NextResponse.json({ error: '验证优惠券失败' }, { status: 500 });
  }
}
