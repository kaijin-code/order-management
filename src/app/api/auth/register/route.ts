import { NextResponse } from 'next/server';
import db from '@/lib/db';
import { hashPassword, generateToken } from '@/lib/auth';

export async function POST(request: Request) {
  try {
    const { username, password } = await request.json();

    if (!username || !password) {
      return NextResponse.json({ error: '用户名和密码不能为空' }, { status: 400 });
    }

    const existingUser = db.prepare('SELECT id FROM users WHERE username = ?').get(username);
    if (existingUser) {
      return NextResponse.json({ error: '用户名已存在' }, { status: 400 });
    }

    const hashedPassword = hashPassword(password);
    const result = db.prepare('INSERT INTO users (username, password) VALUES (?, ?)').run(username, hashedPassword);
    const token = generateToken(result.lastInsertRowid as number, username);

    return NextResponse.json({ token, username });
  } catch (error) {
    console.error('Register error:', error);
    return NextResponse.json({ error: '注册失败' }, { status: 500 });
  }
}
