'use client';

import { useState, useEffect } from 'react';
import { AccountingAPI } from '@/services/api';

export default function SumDisplay() {
  const [total, setTotal] = useState<number>(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadSum = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await AccountingAPI.getSum();
      setTotal(response.total);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load sum');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSum();
  }, []);

  const formatAmount = (amount: number) => {
    return new Intl.NumberFormat('zh-CN', {
      style: 'currency',
      currency: 'CNY'
    }).format(amount);
  };

  return (
    <div className="bg-white rounded-lg shadow p-6">
      <h2 className="text-xl font-semibold mb-4">总计</h2>
      
      {loading ? (
        <div className="text-center py-4">
          <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-2 text-sm text-gray-600">计算中...</p>
        </div>
      ) : error ? (
        <div className="text-center py-4">
          <p className="text-red-600 text-sm">{error}</p>
          <button
            onClick={loadSum}
            className="mt-2 text-blue-600 text-sm hover:underline"
          >
            重试
          </button>
        </div>
      ) : (
        <div className="text-center">
          <div className="text-3xl font-bold text-green-600 mb-2">
            {formatAmount(total)}
          </div>
          <button
            onClick={loadSum}
            className="text-sm text-gray-600 hover:text-gray-800"
          >
            刷新
          </button>
        </div>
      )}
    </div>
  );
}
