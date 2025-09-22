'use client';

import { useState, useEffect } from 'react';
import { AccountingRecord, PageResponse } from '@/types/accounting';
import { AccountingAPI } from '@/services/api';
import RecordList from '@/components/RecordList';
import AddRecordForm from '@/components/AddRecordForm';
import SumDisplay from '@/components/SumDisplay';

export default function Home() {
  const [records, setRecords] = useState<AccountingRecord[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const pageSize = 10;

  const loadRecords = async (page: number = 0) => {
    setLoading(true);
    setError(null);
    try {
      const response: PageResponse<AccountingRecord> = await AccountingAPI.getRecords(page, pageSize);
      setRecords(response.content);
      setTotalPages(response.totalPages);
      setCurrentPage(page);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load records');
    } finally {
      setLoading(false);
    }
  };

  const handleAddRecord = async (record: Omit<AccountingRecord, 'id'>) => {
    try {
      await AccountingAPI.addRecord(record);
      await loadRecords(currentPage);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to add record');
    }
  };

  const handleDeleteRecord = async (id: number) => {
    try {
      await AccountingAPI.deleteRecord(id);
      await loadRecords(currentPage);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to delete record');
    }
  };

  useEffect(() => {
    loadRecords();
  }, []);

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-6xl mx-auto px-4">
        <h1 className="text-3xl font-bold text-gray-900 mb-8 text-center">
          记账管理系统
        </h1>
        
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* 记录列表 */}
          <div className="lg:col-span-2">
            <RecordList
              records={records}
              loading={loading}
              onDelete={handleDeleteRecord}
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={loadRecords}
            />
          </div>

          {/* 侧边栏 */}
          <div className="space-y-6">
            <AddRecordForm onAdd={handleAddRecord} />
            <SumDisplay />
          </div>
        </div>
      </div>
    </div>
  );
}