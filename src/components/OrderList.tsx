'use client';
import { useState, useEffect, useCallback } from 'react';

interface Order {
  id: number;
  order_no: string;
  product_name: string;
  amount: number;
  status: string;
  created_at: string;
}

const statusMap: Record<string, { label: string; color: string }> = {
  pending: { label: '待处理', color: 'bg-yellow-100 text-yellow-800' },
  processing: { label: '处理中', color: 'bg-blue-100 text-blue-800' },
  shipped: { label: '已发货', color: 'bg-purple-100 text-purple-800' },
  delivered: { label: '已送达', color: 'bg-green-100 text-green-800' },
  cancelled: { label: '已取消', color: 'bg-red-100 text-red-800' },
};

interface OrderListProps {
  token: string;
  username: string;
  onLogout: () => void;
}

export default function OrderList({ token, username, onLogout }: OrderListProps) {
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [productName, setProductName] = useState('');
  const [amount, setAmount] = useState('');

  const fetchOrders = useCallback(async () => {
    try {
      const res = await fetch('/api/orders', {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) setOrders(await res.json());
    } finally {
      setLoading(false);
    }
  }, [token]);

  useEffect(() => { fetchOrders(); }, [fetchOrders]);

  const createOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    const res = await fetch('/api/orders', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
      body: JSON.stringify({ productName, amount: parseFloat(amount) }),
    });
    if (res.ok) {
      setProductName(''); setAmount(''); setShowForm(false);
      fetchOrders();
    }
  };

  const updateStatus = async (id: number, status: string) => {
    await fetch(`/api/orders/${id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
      body: JSON.stringify({ status }),
    });
    fetchOrders();
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">订单管理</h1>
          <div className="flex items-center gap-4">
            <span className="text-gray-600">欢迎, {username}</span>
            <button onClick={onLogout} className="text-red-500 hover:underline">退出</button>
          </div>
        </div>

        <button
          onClick={() => setShowForm(!showForm)}
          className="mb-4 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          {showForm ? '取消' : '创建订单'}
        </button>

        {showForm && (
          <form onSubmit={createOrder} className="bg-white p-4 rounded-lg shadow mb-4 flex gap-4">
            <input
              type="text" placeholder="商品名称" value={productName}
              onChange={(e) => setProductName(e.target.value)}
              className="flex-1 p-2 border rounded text-gray-800" required
            />
            <input
              type="number" placeholder="金额" value={amount} step="0.01"
              onChange={(e) => setAmount(e.target.value)}
              className="w-32 p-2 border rounded text-gray-800" required
            />
            <button type="submit" className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
              提交
            </button>
          </form>
        )}

        {loading ? (
          <p className="text-center text-gray-500">加载中...</p>
        ) : orders.length === 0 ? (
          <p className="text-center text-gray-500">暂无订单</p>
        ) : (
          <div className="space-y-4">
            {orders.map((order) => (
              <div key={order.id} className="bg-white p-4 rounded-lg shadow">
                <div className="flex justify-between items-start">
                  <div>
                    <p className="font-semibold text-gray-800">{order.product_name}</p>
                    <p className="text-sm text-gray-500">订单号: {order.order_no}</p>
                    <p className="text-sm text-gray-500">金额: ¥{order.amount.toFixed(2)}</p>
                    <p className="text-sm text-gray-500">创建时间: {new Date(order.created_at).toLocaleString()}</p>
                  </div>
                  <div className="flex flex-col items-end gap-2">
                    <span className={`px-3 py-1 rounded-full text-sm ${statusMap[order.status]?.color}`}>
                      {statusMap[order.status]?.label}
                    </span>
                    <select
                      value={order.status}
                      onChange={(e) => updateStatus(order.id, e.target.value)}
                      className="text-sm border rounded p-1 text-gray-800"
                    >
                      {Object.entries(statusMap).map(([key, { label }]) => (
                        <option key={key} value={key}>{label}</option>
                      ))}
                    </select>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
