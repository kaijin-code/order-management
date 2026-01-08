import { NextResponse } from 'next/server';
import { verifyToken } from '@/lib/auth';
import { couponService } from '@/grpc/client';

function getUserFromRequest(request: Request) {
  const authHeader = request.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) return null;
  return verifyToken(authHeader.slice(7));
}

// 获取用户的优惠券列表
export async function GET(request: Request) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  try {
    const { searchParams } = new URL(request.url);
    const status = searchParams.get('status') || undefined;

    const result = await couponService.getUserCoupons(user.userId, status);
    return NextResponse.json(result);
  } catch (error) {
    console.error('获取用户优惠券失败:', error);
    return NextResponse.json({ error: '获取用户优惠券失败' }, { status: 500 });
  }
}
