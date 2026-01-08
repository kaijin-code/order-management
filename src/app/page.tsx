'use client';
import { useState, useEffect, useCallback } from 'react';
import AuthForm from '@/components/AuthForm';
import OrderList from '@/components/OrderList';

export default function Home() {
  const [authState, setAuthState] = useState<{
    token: string | null;
    username: string;
    loading: boolean;
  }>({ token: null, username: '', loading: true });

  const initAuth = useCallback(() => {
    const savedToken = localStorage.getItem('token');
    const savedUsername = localStorage.getItem('username');
    setAuthState({
      token: savedToken && savedUsername ? savedToken : null,
      username: savedUsername || '',
      loading: false,
    });
  }, []);

  useEffect(() => { initAuth(); }, [initAuth]);

  const handleLogin = (newToken: string, newUsername: string) => {
    setAuthState({ token: newToken, username: newUsername, loading: false });
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    setAuthState({ token: null, username: '', loading: false });
  };

  if (authState.loading) {
    return <div className="min-h-screen flex items-center justify-center">加载中...</div>;
  }

  return authState.token ? (
    <OrderList token={authState.token} username={authState.username} onLogout={handleLogout} />
  ) : (
    <AuthForm onSuccess={handleLogin} />
  );
}
