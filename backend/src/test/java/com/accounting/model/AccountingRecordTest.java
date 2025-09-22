package test.java.com.accounting.model;
import com.accounting.model.AccountingRecord;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountingRecordTest {

    @Test
    void gettersAndSetters_work() {
        AccountingRecord r = new AccountingRecord();
        r.setId(5L);
        r.setDate(LocalDate.of(2024, 2, 3));
        r.setAmount(new BigDecimal("9.99"));
        r.setContent("memo");

        assertEquals(5L, r.getId());
        assertEquals(LocalDate.of(2024, 2, 3), r.getDate());
        assertEquals(new BigDecimal("9.99"), r.getAmount());
        assertEquals("memo", r.getContent());

        AccountingRecord r2 = new AccountingRecord(LocalDate.of(2024, 1, 1), new BigDecimal("1.23"), "x");
        assertNull(r2.getId());
        assertEquals(LocalDate.of(2024, 1, 1), r2.getDate());
        assertEquals(new BigDecimal("1.23"), r2.getAmount());
        assertEquals("x", r2.getContent());
    }
}


