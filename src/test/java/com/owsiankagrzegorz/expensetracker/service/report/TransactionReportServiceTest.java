package com.owsiankagrzegorz.expensetracker.service.report;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

class TransactionReportServiceTest {

    private TransactionReportService reportService;

    @BeforeEach
    void setUp() {
        TransactionRepository repo = new InMemoryTransactionRepository();
        reportService = new TransactionReportService(repo);

        repo.add(new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.of(2026, 1, 10), TransactionType.WYDATEK));
        repo.add(new Transaction(2L, bd("200.00"), "Transport", LocalDate.of(2026, 1, 11), TransactionType.WYDATEK));
        repo.add(new Transaction(3L, bd("500.00"), "Wypłata", LocalDate.of(2026, 1, 15), TransactionType.PRZYCHOD));
        repo.add(new Transaction(4L, bd("50.00"), "Jedzenie", LocalDate.of(2026, 2, 1), TransactionType.WYDATEK));
        repo.add(new Transaction(5L, bd("700.00"), "Wypłata", LocalDate.of(2026, 2, 5), TransactionType.PRZYCHOD));
    }

    @Test
    void shouldReportPeriodIncomeExpenseAndBalance() {
        var summary = reportService.reportPeriod(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );

        assertMoneyEquals("500.00", summary.getIncomeTotal());
        assertMoneyEquals("300.00", summary.getExpenseTotal());
        assertMoneyEquals("200.00", summary.getBalance());
    }

    @Test
    void shouldReportMonthlySummary() {
        var month = YearMonth.of(2026, 2);
        var summary = reportService.reportMonthly(month);

        assertEquals(month, summary.getMonth());
        assertMoneyEquals("700.00", summary.getIncomeTotal());
        assertMoneyEquals("50.00", summary.getExpenseTotal());
        assertMoneyEquals("650.00", summary.getBalance());
    }

    @Test
    void shouldReportCategoryBreakdownForExpensesInPeriodSortedDesc() {
        var breakdown = reportService.reportByCategory(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 2, 28),
                TransactionType.WYDATEK
        );

        var totals = breakdown.getTotalsByCategory();

        assertEquals(2, totals.size());
        assertMoneyEquals("200.00", totals.get("Transport"));
        assertMoneyEquals("150.00", totals.get("Jedzenie"));

        // If your report returns LinkedHashMap sorted desc (as in your service implementation), this remains valid:
        assertEquals("Transport", totals.keySet().iterator().next());
    }

    @Test
    void shouldReportCategoryBreakdownForAllTypesWhenTypeIsNull() {
        var breakdown = reportService.reportByCategory(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 2, 28),
                null
        );

        var totals = breakdown.getTotalsByCategory();

        assertEquals(3, totals.size());
        assertMoneyEquals("1200.00", totals.get("Wypłata"));
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    private static void assertMoneyEquals(String expected, BigDecimal actual) {
        assertNotNull(actual);
        assertEquals(0, new BigDecimal(expected).compareTo(actual),
                () -> "expected=" + expected + " actual=" + actual);
    }
}