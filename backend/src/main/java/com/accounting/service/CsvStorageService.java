package com.accounting.service;

import com.accounting.model.AccountingRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvStorageService {
    private static final String CSV_FILE = "data.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<AccountingRecord> loadRecords() {
        List<AccountingRecord> records = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(CSV_FILE))) {
                List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            AccountingRecord record = new AccountingRecord();
                            record.setId((long) i);
                            record.setDate(LocalDate.parse(parts[0], DATE_FORMATTER));
                            record.setAmount(new BigDecimal(parts[1]));
                            record.setContent(parts[2]);
                            records.add(record);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void saveRecords(List<AccountingRecord> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (AccountingRecord record : records) {
                writer.println(record.getDate().format(DATE_FORMATTER) + "," + 
                             record.getAmount() + "," + 
                             record.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(AccountingRecord record) {
        List<AccountingRecord> records = loadRecords();
        record.setId((long) records.size());
        records.add(record);
        saveRecords(records);
    }

    public void deleteRecord(Long id) {
        List<AccountingRecord> records = loadRecords();
        records.removeIf(record -> record.getId().equals(id));
        // Reassign IDs
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setId((long) i);
        }
        saveRecords(records);
    }

    public BigDecimal calculateSum() {
        List<AccountingRecord> records = loadRecords();
        return records.stream()
                .map(AccountingRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
