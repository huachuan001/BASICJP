package com.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountingRecord {
    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private String content;

    public AccountingRecord() {}

    public AccountingRecord(LocalDate date, BigDecimal amount, String content) {
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
