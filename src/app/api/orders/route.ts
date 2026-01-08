import { NextResponse } from 'next/server';
import db from '@/lib/db';
import { verifyToken } from '@/lib/auth';
import { couponService } from '@/grpc/client';

function getUserFromRequest(request: Request) {
  const authHeader = request.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) return null;
  return verifyToken(authHeader.slice(7));
}

export async function GET(request: Request) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  const orders = db.prepare(`
    SELECT id, order_no, product_name, amount, discount_amount, final_amount, coupon_code, status, created_at, updated_at 
    FROM orders WHERE user_id = ? ORDER BY created_at DESC
  `).all(user.userId);

  return NextResponse.json(orders);
}

export async function POST(request: Request) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  try {
    const { productName, amount, couponCode } = await request.json();
    if (!productName || !amount) {
      return NextResponse.json({ error: '商品名称和金额不能为空' }, { status: 400 });
    }

    let discountAmount = 0;
    let finalAmount = amount;

    // 如果使用优惠券，先验证
    if (couponCode) {
      try {
        const validateResult = await couponService.validateCoupon(couponCode, user.userId, amount) as { valid: boolean; message?: string; discount_amount: number; final_amount: number };
        if (!validateResult.valid) {
          return NextResponse.json({ error: validateResult.message || '优惠券无效' }, { status: 400 });
        }
        discountAmount = validateResult.discount_amount;
        finalAmount = validateResult.final_amount;
      } catch (grpcError) {
        console.error('优惠券验证失败:', grpcError);
        return NextResponse.json({ error: '优惠券服务暂不可用' }, { status: 503 });
      }
    }

    const orderNo = `ORD${Date.now()}${Math.random().toString(36).slice(2, 6).toUpperCase()}`;
    
    const result = db.prepare(`
      INSERT INTO orders (user_id, order_no, product_name, amount, discount_amount, final_amount, coupon_code, status) 
      VALUES (?, ?, ?, ?, ?, ?, ?, 'pending')
    `).run(user.userId, orderNo, productName, amount, discountAmount, finalAmount, couponCode || null);

    // 如果使用了优惠券，调用 gRPC 标记优惠券已使用
    if (couponCode && result.lastInsertRowid) {
      try {
        await couponService.useCoupon(couponCode, user.userId, Number(result.lastInsertRowid), amount);
      } catch (grpcError) {
        // 优惠券使用失败，回滚订单
        db.prepare('DELETE FROM orders WHERE id = ?').run(result.lastInsertRowid);
        console.error('优惠券使用失败:', grpcError);
        return NextResponse.json({ error: '优惠券使用失败，请重试' }, { status: 500 });
      }
    }

    return NextResponse.json({ 
      message: '订单创建成功', 
      orderNo,
      amount,
      discountAmount,
      finalAmount
    });
  } catch (error) {
    console.error('Create order error:', error);
    return NextResponse.json({ error: '创建订单失败' }, { status: 500 });
  }
}
