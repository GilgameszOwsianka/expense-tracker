package com.owsiankagrzegorz.expensetracker.service.report.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeriodSummaryTest {

    @Test
    void shouldCreatePeriodSummaryAndComputeBalance() {

        PeriodSummary summary = new PeriodSummary(
                new BigDecimal("900.00"),
                new BigDecimal("400.00")
        );

        assertEquals(new BigDecimal("900.00"), summary.getIncomeTotal());
        assertEquals(new BigDecimal("400.00"), summary.getExpenseTotal());
        assertEquals(new BigDecimal("500.00"), summary.getBalance());
    }
}