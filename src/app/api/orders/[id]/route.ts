import { NextResponse } from 'next/server';
import db from '@/lib/db';
import { verifyToken } from '@/lib/auth';

function getUserFromRequest(request: Request) {
  const authHeader = request.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) return null;
  return verifyToken(authHeader.slice(7));
}

export async function PATCH(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const user = getUserFromRequest(request);
  if (!user) {
    return NextResponse.json({ error: '未授权' }, { status: 401 });
  }

  try {
    const { id } = await params;
    const { status } = await request.json();
    const validStatuses = ['pending', 'processing', 'shipped', 'delivered', 'cancelled'];
    
    if (!validStatuses.includes(status)) {
      return NextResponse.json({ error: '无效的订单状态' }, { status: 400 });
    }

    const result = db.prepare(`
      UPDATE orders SET status = ?, updated_at = CURRENT_TIMESTAMP 
      WHERE id = ? AND user_id = ?
    `).run(status, id, user.userId);

    if (result.changes === 0) {
      return NextResponse.json({ error: '订单不存在' }, { status: 404 });
    }

    return NextResponse.json({ message: '订单状态更新成功' });
  } catch (error) {
    console.error('Update order error:', error);
    return NextResponse.json({ error: '更新订单失败' }, { status: 500 });
  }
}
