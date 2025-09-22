import { AccountingRecord, PageResponse, SumResponse } from '@/types/accounting';

const API_BASE_URL = 'http://localhost:8080/api/accounting';

export class AccountingAPI {
  static async getRecords(page: number = 0, size: number = 10): Promise<PageResponse<AccountingRecord>> {
    const response = await fetch(`${API_BASE_URL}/records?page=${page}&size=${size}`);
    if (!response.ok) {
      throw new Error('Failed to fetch records');
    }
    return response.json();
  }

  static async addRecord(record: Omit<AccountingRecord, 'id'>): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/add`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(record),
    });
    if (!response.ok) {
      throw new Error('Failed to add record');
    }
  }

  static async deleteRecord(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/delete/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      throw new Error('Failed to delete record');
    }
  }

  static async getSum(): Promise<SumResponse> {
    const response = await fetch(`${API_BASE_URL}/sum`);
    if (!response.ok) {
      throw new Error('Failed to calculate sum');
    }
    return response.json();
  }
}
