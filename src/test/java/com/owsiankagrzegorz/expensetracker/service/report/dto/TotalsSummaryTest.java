package com.owsiankagrzegorz.expensetracker.service.report.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalsSummaryTest {

    @Test
    void shouldCreateTotalsSummaryAndComputeBalance() {
        TotalsSummary summary = new TotalsSummary(
                bd("1200.00"),
                bd("350.00")
        );

        assertEquals(bd("1200.00"), summary.getIncomeTotal());
        assertEquals(bd("350.00"), summary.getExpenseTotal());
        assertEquals(bd("850.00"), summary.getBalance());
    }

    @Test
    void shouldComputeBalance() {
        TotalsSummary summary = new TotalsSummary(
                bd("1200.00"),
                bd("350.00")
        );

        assertEquals(bd("850.00"), summary.getBalance());
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}