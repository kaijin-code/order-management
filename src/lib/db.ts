import Database from 'better-sqlite3';
import path from 'path';

const dbPath = path.join(process.cwd(), 'data.db');
const db = new Database(dbPath);

// 初始化数据库表
db.exec(`
  CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    order_no TEXT UNIQUE NOT NULL,
    product_name TEXT NOT NULL,
    amount REAL NOT NULL,
    discount_amount REAL DEFAULT 0,
    final_amount REAL NOT NULL,
    coupon_code TEXT,
    status TEXT DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
  );
`);

// 迁移：为旧表添加优惠券相关字段
try {
  db.exec(`ALTER TABLE orders ADD COLUMN discount_amount REAL DEFAULT 0`);
} catch {}
try {
  db.exec(`ALTER TABLE orders ADD COLUMN final_amount REAL`);
  db.exec(`UPDATE orders SET final_amount = amount WHERE final_amount IS NULL`);
} catch {}
try {
  db.exec(`ALTER TABLE orders ADD COLUMN coupon_code TEXT`);
} catch {}

export default db;
