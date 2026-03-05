package com.owsiankagrzegorz.expensetracker.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    LocalDate date = LocalDate.of(2025, 9, 7);

    @Test
    void shouldCreateTransactionWithGivenValues() {
        Transaction t = new Transaction(
                1L,
                new BigDecimal("200.00"),
                "Transport",
                LocalDate.of(2025, 9, 7),
                TransactionType.WYDATEK
        );

        assertEquals(1L, t.getId());
        assertEquals(new BigDecimal("200.00"), t.getAmount());
        assertEquals("Transport", t.getCategory());
        assertEquals(date, t.getDate());
        assertEquals(TransactionType.WYDATEK, t.getType());
    }
}