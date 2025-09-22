package test.java.com.accounting.service;

import com.accounting.model.AccountingRecord;
import com.accounting.model.PageResponse;
import com.accounting.service.AccountingService;
import com.accounting.service.CsvStorageService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountingServiceTest {

    static class InMemoryStorage extends CsvStorageService {
        private final List<AccountingRecord> records = new ArrayList<>();

        @Override
        public List<AccountingRecord> loadRecords() {
            return new ArrayList<>(records);
        }

        @Override
        public void saveRecords(List<AccountingRecord> newRecords) {
            records.clear();
            for (int i = 0; i < newRecords.size(); i++) {
                AccountingRecord r = newRecords.get(i);
                r.setId((long) i);
                records.add(r);
            }
        }

        @Override
        public void addRecord(AccountingRecord record) {
            record.setId((long) records.size());
            records.add(record);
        }

        @Override
        public void deleteRecord(Long id) {
            records.removeIf(r -> r.getId().equals(id));
            for (int i = 0; i < records.size(); i++) {
                records.get(i).setId((long) i);
            }
        }

        @Override
        public BigDecimal calculateSum() {
            return records.stream().map(AccountingRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        @Override
        public void deleteAll() {
            records.clear();
        }
    }

    @Test
    void getRecords_paginatesCorrectly() {
        AccountingService service = new AccountingService();
        InMemoryStorage storage = new InMemoryStorage();
        try {
            var f = AccountingService.class.getDeclaredField("csvStorageService");
            f.setAccessible(true);
            f.set(service, storage);
        } catch (Exception e) {
            fail(e);
        }

        storage.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("1"), "a"));
        storage.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("2"), "b"));
        storage.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("3"), "c"));

        PageResponse<AccountingRecord> page = service.getRecords(1, 2);
        assertEquals(1, page.getPage());
        assertEquals(2, page.getSize());
        assertEquals(3, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getContent().size());
        assertEquals("c", page.getContent().get(0).getContent());
    }

    @Test
    void delegateMethods_callStorage() {
        AccountingService service = new AccountingService();
        InMemoryStorage storage = new InMemoryStorage();
        try {
            var f = AccountingService.class.getDeclaredField("csvStorageService");
            f.setAccessible(true);
            f.set(service, storage);
        } catch (Exception e) {
            fail(e);
        }

        AccountingRecord rec = new AccountingRecord(LocalDate.now(), new BigDecimal("10"), "x");
        service.addRecord(rec);
        assertEquals(1, storage.loadRecords().size());

        service.deleteRecord(0L);
        assertEquals(0, storage.loadRecords().size());

        service.calculateSum();
        assertEquals(BigDecimal.ZERO, storage.calculateSum());

        storage.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("5"), "y"));
        storage.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("7"), "z"));
        assertEquals(new BigDecimal("12"), service.calculateSum());

        service.deleteAll();
        assertTrue(storage.loadRecords().isEmpty());
    }
}


