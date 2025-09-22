package com.accounting.service;

import com.accounting.model.AccountingRecord;
import com.accounting.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountingService {
    
    @Autowired
    private CsvStorageService csvStorageService;

    public PageResponse<AccountingRecord> getRecords(int page, int size) {
        List<AccountingRecord> allRecords = csvStorageService.loadRecords();
        long totalElements = allRecords.size();
        
        int start = page * size;
        int end = Math.min(start + size, allRecords.size());
        
        List<AccountingRecord> pageContent = allRecords.subList(start, end);
        
        return new PageResponse<>(pageContent, page, size, totalElements);
    }

    public void addRecord(AccountingRecord record) {
        csvStorageService.addRecord(record);
    }

    public void deleteRecord(Long id) {
        csvStorageService.deleteRecord(id);
    }

    public BigDecimal calculateSum() {
        return csvStorageService.calculateSum();
    }

    public void deleteAll() {
        csvStorageService.deleteAll();
    }
}
