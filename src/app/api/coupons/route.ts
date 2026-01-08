import { NextResponse } from 'next/server';
import { verifyToken } from '@/lib/auth';
import { couponService } from '@/grpc/client';

function getUserFromRequest(request: Request) {
  const authHeader = request.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) return null;
  return verifyToken(authHeader.slice(7));
}

// 获取可用优惠券模板列表
export async function GET(request: Request) {
  try {
    const { searchParams } = new URL(request.url);
    const page = parseInt(searchParams.get('page') || '1');
    const pageSize = parseInt(searchParams.get('pageSize') || '10');

    const result = await couponService.listAvailableTemplates(page, pageSize);
    return NextResponse.json(result);
  } catch (error) {
    console.error('获取优惠券模板失败:', error);
    return NextResponse.json({ error: '获取优惠券模板失败' }, { status: 500 });
  }
}

// 领取优惠券
export async function POST(request: Request) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  try {
    const { templateId } = await request.json();
    if (!templateId) {
      return NextResponse.json({ error: '优惠券模板ID不能为空' }, { status: 400 });
    }

    const result = await couponService.claimCoupon(user.userId, templateId);
    return NextResponse.json(result);
  } catch (error) {
    console.error('领取优惠券失败:', error);
    return NextResponse.json({ error: '领取优惠券失败' }, { status: 500 });
  }
}
