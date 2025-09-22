package test.java.com.accounting.controller;

import com.accounting.controller.AccountingController;
import com.accounting.model.AccountingRecord;
import com.accounting.model.PageResponse;
import com.accounting.service.AccountingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountingControllerTest {

    private MockMvc mockMvc;

    static class FakeService extends AccountingService {
        private final java.util.List<AccountingRecord> storage = new ArrayList<>();
        private boolean throwOnAdd = false;
        private boolean throwOnDelete = false;
        private boolean throwOnDeleteAll = false;

        void setThrowOnAdd(boolean v) { this.throwOnAdd = v; }
        void setThrowOnDelete(boolean v) { this.throwOnDelete = v; }
        void setThrowOnDeleteAll(boolean v) { this.throwOnDeleteAll = v; }

        @Override
        public PageResponse<AccountingRecord> getRecords(int page, int size) {
            int start = Math.min(page * size, storage.size());
            int end = Math.min(start + size, storage.size());
            java.util.List<AccountingRecord> content = storage.subList(start, end);
            return new PageResponse<>(content, page, size, storage.size());
        }

        @Override
        public void addRecord(AccountingRecord record) {
            if (throwOnAdd) throw new RuntimeException("boom");
            storage.add(record);
        }

        @Override
        public void deleteRecord(Long id) {
            if (throwOnDelete) throw new RuntimeException("boom");
            storage.removeIf(r -> id.equals(r.getId()));
        }

        @Override
        public BigDecimal calculateSum() {
            return storage.stream().map(AccountingRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        @Override
        public void deleteAll() {
            if (throwOnDeleteAll) throw new RuntimeException("boom");
            storage.clear();
        }
    }

    private FakeService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new FakeService();
        AccountingController controller = new AccountingController();
        var f = AccountingController.class.getDeclaredField("accountingService");
        f.setAccessible(true);
        f.set(controller, service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getRecords_ok() throws Exception {
        mockMvc.perform(get("/api/accounting/records"))
                .andExpect(status().isOk());
    }

    @Test
    void addRecord_ok_and_badRequest() throws Exception {
        String json = "{\"date\":\"2024-01-01\",\"amount\":10,\"content\":\"x\"}";
        mockMvc.perform(post("/api/accounting/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record added successfully"));

        service.setThrowOnAdd(true);
        mockMvc.perform(post("/api/accounting/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void deleteRecord_ok_and_badRequest() throws Exception {
        mockMvc.perform(delete("/api/accounting/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record deleted successfully"));

        service.setThrowOnDelete(true);
        mockMvc.perform(delete("/api/accounting/delete/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void sum_ok() throws Exception {
        service.addRecord(new AccountingRecord(LocalDate.now(), new BigDecimal("12.34"), "a"));
        mockMvc.perform(get("/api/accounting/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(12.34));
    }

    @Test
    void deleteAll_ok_and_badRequest() throws Exception {
        mockMvc.perform(delete("/api/accounting/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All records deleted successfully"));

        service.setThrowOnDeleteAll(true);
        mockMvc.perform(delete("/api/accounting/deleteAll"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}


