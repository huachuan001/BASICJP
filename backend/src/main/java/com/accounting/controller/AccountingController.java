package com.accounting.controller;

import com.accounting.model.AccountingRecord;
import com.accounting.model.PageResponse;
import com.accounting.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounting")
@CrossOrigin(origins = "*")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    @GetMapping("/records")
    public ResponseEntity<PageResponse<AccountingRecord>> getRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AccountingRecord> response = accountingService.getRecords(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRecord(@RequestBody AccountingRecord record) {
        try {
            accountingService.addRecord(record);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Record added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to add record: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteRecord(@PathVariable Long id) {
        try {
            accountingService.deleteRecord(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Record deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete record: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/sum")
    public ResponseEntity<Map<String, BigDecimal>> calculateSum() {
        BigDecimal sum = accountingService.calculateSum();
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("total", sum);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Map<String, String>> deleteAll() {
        try {
            accountingService.deleteAll();
            Map<String, String> response = new HashMap<>();
            response.put("message", "All records deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete all records: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
