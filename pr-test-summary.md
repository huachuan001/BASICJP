### Backend Unit Tests Summary

- **Tests run**: 12
- **Failures**: 0
- **Errors**: 0
- **Skipped**: 0
- **Line coverage**: 93% (covered 701 / total 751)

> ✅ All tests passed. Coverage gate enforced at 100%.

#### Detailed Test Cases

| Class | Test | Status | Time (s) | Message |
|---|---|---:|---:|---|
| test.java.com.accounting.controller.AccountingControllerTest | addRecord_ok_and_badRequest | PASS | 0.04 |  |
| test.java.com.accounting.controller.AccountingControllerTest | deleteAll_ok_and_badRequest | PASS | 0.005 |  |
| test.java.com.accounting.controller.AccountingControllerTest | deleteRecord_ok_and_badRequest | PASS | 0.479 |  |
| test.java.com.accounting.controller.AccountingControllerTest | getRecords_ok | PASS | 0.012 |  |
| test.java.com.accounting.controller.AccountingControllerTest | sum_ok | PASS | 0.029 |  |
| test.java.com.accounting.model.AccountingRecordTest | gettersAndSetters_work | PASS | 0.001 |  |
| test.java.com.accounting.model.PageResponseTest | constructorCalculatesTotalPages_andGettersSetters | PASS | 0.002 |  |
| test.java.com.accounting.service.AccountingServiceTest | delegateMethods_callStorage | PASS | 0.0 |  |
| test.java.com.accounting.service.AccountingServiceTest | getRecords_paginatesCorrectly | PASS | 0.001 |  |
| test.java.com.accounting.service.CsvStorageServiceTest | loadRecords_whenFileDoesNotExist_returnsEmpty | PASS | 0.002 |  |
| test.java.com.accounting.service.CsvStorageServiceTest | loadRecords_whenFileIsDirectory_triggersCatchAndReturnsEmpty | PASS | 0.001 |  |
| test.java.com.accounting.service.CsvStorageServiceTest | saveLoadAddDeleteCalculateSumAndDeleteAll_coverAllPaths | PASS | 0.002 |  |
