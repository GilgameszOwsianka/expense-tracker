package com.owsiankagrzegorz.expensetracker.service.report.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CategoryBreakdownTest {

    @Test
    void shouldStoreTotalsByCategory() {

        Map<String, BigDecimal> input = new LinkedHashMap<>();
        input.put("Food", new BigDecimal("120.00"));
        input.put("Transport", new BigDecimal("80.00"));

        CategoryBreakdown breakdown = new CategoryBreakdown(input);

        assertEquals(2, breakdown.getTotalsByCategory().size());
        assertEquals(new BigDecimal("120.00"), breakdown.getTotalsByCategory().get("Food"));
    }

    @Test
    void shouldExposeUnmodifiableMap() {

        Map<String, BigDecimal> input = new LinkedHashMap<>();
        input.put("Food", new BigDecimal("120.00"));

        CategoryBreakdown breakdown = new CategoryBreakdown(input);

        assertThrows(
                UnsupportedOperationException.class,
                () -> breakdown.getTotalsByCategory().put("Other", new BigDecimal("50.00"))
        );
    }
}