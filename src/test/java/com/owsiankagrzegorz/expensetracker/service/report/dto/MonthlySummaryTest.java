package com.owsiankagrzegorz.expensetracker.service.report.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonthlySummaryTest {

    @Test
    void shouldCreateMonthlySummaryAndComputeBalance() {

        YearMonth month = YearMonth.of(2026, 3);

        MonthlySummary summary = new MonthlySummary(
                month,
                new BigDecimal("1000.00"),
                new BigDecimal("250.00")
        );

        assertEquals(month, summary.getMonth());
        assertEquals(new BigDecimal("1000.00"), summary.getIncomeTotal());
        assertEquals(new BigDecimal("250.00"), summary.getExpenseTotal());
        assertEquals(new BigDecimal("750.00"), summary.getBalance());
    }
}