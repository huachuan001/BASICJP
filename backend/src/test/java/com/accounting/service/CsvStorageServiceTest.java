package test.java.com.accounting.service;

import com.accounting.model.AccountingRecord;
import com.accounting.service.CsvStorageService;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageServiceTest {

    private static final String CSV_NAME = "data.csv";
    private final CsvStorageService csvStorageService = new CsvStorageService();
    private static byte[] originalBytes;
    private static boolean originalExists;

    @BeforeAll
    static void backupOriginal() throws IOException {
        Path csvPath = Path.of(CSV_NAME);
        originalExists = Files.exists(csvPath);
        if (originalExists && Files.isRegularFile(csvPath)) {
            originalBytes = Files.readAllBytes(csvPath);
        }
    }

    @AfterAll
    static void restoreOriginal() throws IOException {
        Path csvPath = Path.of(CSV_NAME);
        if (originalExists) {
            if (originalBytes != null) {
                Files.write(csvPath, originalBytes);
            } else if (!Files.exists(csvPath)) {
                Files.createFile(csvPath);
            }
        } else {
            if (Files.exists(csvPath)) {
                Files.delete(csvPath);
            }
        }
    }

    @AfterEach
    void cleanupDirectoryNamedCsv() throws IOException {
        Path csvPath = Path.of(CSV_NAME);
        if (Files.exists(csvPath) && Files.isDirectory(csvPath)) {
            Files.delete(csvPath);
        }
    }

    @Test
    void loadRecords_whenFileDoesNotExist_returnsEmpty() {
        Path csvPath = Path.of(CSV_NAME);
        try {
            if (Files.exists(csvPath)) {
                Files.delete(csvPath);
            }
        } catch (IOException e) {
            // ignore
        }
        List<AccountingRecord> records = csvStorageService.loadRecords();
        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test
    void loadRecords_whenFileIsDirectory_triggersCatchAndReturnsEmpty() throws IOException {
        Path csvPath = Path.of(CSV_NAME);
        if (Files.exists(csvPath)) {
            Files.delete(csvPath);
        }
        Files.createDirectory(csvPath);

        List<AccountingRecord> records = csvStorageService.loadRecords();
        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test
    void saveLoadAddDeleteCalculateSumAndDeleteAll_coverAllPaths() throws IOException {
        // Ensure clean file
        Path csvPath = Path.of(CSV_NAME);
        if (Files.exists(csvPath)) {
            if (Files.isDirectory(csvPath)) Files.delete(csvPath);
            else Files.delete(csvPath);
        }

        // Start with empty list and save
        List<AccountingRecord> initial = new ArrayList<>();
        csvStorageService.saveRecords(initial);
        assertTrue(Files.exists(csvPath));

        // Add records
        AccountingRecord r1 = new AccountingRecord(LocalDate.of(2024, 1, 1), new BigDecimal("10.50"), "coffee");
        AccountingRecord r2 = new AccountingRecord(LocalDate.of(2024, 1, 2), new BigDecimal("20.25"), "lunch");
        csvStorageService.addRecord(r1);
        csvStorageService.addRecord(r2);

        List<AccountingRecord> loaded = csvStorageService.loadRecords();
        assertEquals(2, loaded.size());
        assertEquals(0L, loaded.get(0).getId());
        assertEquals(1L, loaded.get(1).getId());

        // Sum
        BigDecimal sum = csvStorageService.calculateSum();
        assertEquals(new BigDecimal("30.75"), sum);

        // Delete one and ensure reindex
        csvStorageService.deleteRecord(0L);
        List<AccountingRecord> afterDelete = csvStorageService.loadRecords();
        assertEquals(1, afterDelete.size());
        assertEquals(0L, afterDelete.get(0).getId());
        assertEquals("lunch", afterDelete.get(0).getContent());

        // deleteAll normal path: empties file
        csvStorageService.deleteAll();
        List<AccountingRecord> afterDeleteAll = csvStorageService.loadRecords();
        assertTrue(afterDeleteAll.isEmpty());

        // saveRecords exception path: make a directory named data.csv
        Files.deleteIfExists(csvPath);
        Files.createDirectory(csvPath);
        csvStorageService.saveRecords(initial); // should hit catch but not throw
        assertTrue(Files.isDirectory(csvPath));

        // deleteAll exception path: still a directory
        csvStorageService.deleteAll(); // catch path
        assertTrue(Files.isDirectory(csvPath));
    }
}


