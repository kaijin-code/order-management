import { NextResponse } from 'next/server';
import db from '@/lib/db';
import { verifyPassword, generateToken } from '@/lib/auth';

interface User {
  id: number;
  username: string;
  password: string;
}

export async function POST(request: Request) {
  try {
    const { username, password } = await request.json();

    if (!username || !password) {
      return NextResponse.json({ error: '用户名和密码不能为空' }, { status: 400 });
    }

    const user = db.prepare('SELECT * FROM users WHERE username = ?').get(username) as User | undefined;
    if (!user || !verifyPassword(password, user.password)) {
      return NextResponse.json({ error: '用户名或密码错误' }, { status: 401 });
    }

    const token = generateToken(user.id, user.username);
    return NextResponse.json({ token, username: user.username });
  } catch (error) {
    console.error('Login error:', error);
    return NextResponse.json({ error: '登录失败' }, { status: 500 });
  }
}
