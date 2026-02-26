package com.owsiankagrzegorz.expensetracker.service.report;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.query.TransactionQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

class TransactionReportServiceTest {

    private TransactionReportService reportService;

    @BeforeEach
    void setUp() {
        TransactionRepository repo = new InMemoryTransactionRepository();
        reportService = new TransactionReportService(repo);

        repo.add(new Transaction(1L, 100.0, "Jedzenie", LocalDate.of(2026, 1, 10), TransactionType.WYDATEK));
        repo.add(new Transaction(2L, 200.0, "Transport", LocalDate.of(2026, 1, 11), TransactionType.WYDATEK));
        repo.add(new Transaction(3L, 500.0, "Wypłata", LocalDate.of(2026, 1, 15), TransactionType.PRZYCHOD));
        repo.add(new Transaction(4L, 50.0, "Jedzenie", LocalDate.of(2026, 2, 1), TransactionType.WYDATEK));
        repo.add(new Transaction(5L, 700.0, "Wypłata", LocalDate.of(2026, 2, 5), TransactionType.PRZYCHOD));
    }

    @Test
    void shouldReportPeriodIncomeExpenseAndBalance() {
        var summary = reportService.reportPeriod(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );

        assertEquals(500.0, summary.getIncomeTotal());
        assertEquals(300.0, summary.getExpenseTotal());
        assertEquals(200.0, summary.getBalance());
    }

    @Test
    void shouldReportMonthlySummary() {
        var month = YearMonth.of(2026, 2);
        var summary = reportService.reportMonthly(month);

        assertEquals(month, summary.getMonth());
        assertEquals(700.0, summary.getIncomeTotal());
        assertEquals(50.0, summary.getExpenseTotal());
        assertEquals(650.0, summary.getBalance());
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
        assertEquals(200.0, totals.get("Transport"));
        assertEquals(150.0, totals.get("Jedzenie"));

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
        assertEquals(1200.0, totals.get("Wypłata"));
    }
}
