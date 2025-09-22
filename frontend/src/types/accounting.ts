export interface AccountingRecord {
  id: number;
  date: string;
  amount: number;
  content: string;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface SumResponse {
  total: number;
}
