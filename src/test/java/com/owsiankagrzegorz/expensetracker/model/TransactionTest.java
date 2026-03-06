package com.owsiankagrzegorz.expensetracker.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

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
        assertEquals(LocalDate.of(2025, 9, 7), t.getDate());
        assertEquals(TransactionType.WYDATEK, t.getType());
    }

    @Test
    void shouldNormalizeAmountInConstructor() {
        Transaction t = new Transaction(
                1L,
                new BigDecimal("12.345"),
                "Transport",
                LocalDate.of(2025, 9, 7),
                TransactionType.WYDATEK
        );

        assertEquals(new BigDecimal("12.35"), t.getAmount());
    }

    @Test
    void shouldNormalizeAmountInSetter() {
        Transaction t = new Transaction();
        t.setAmount(new BigDecimal("9.994"));

        assertEquals(new BigDecimal("9.99"), t.getAmount());
    }

    @Test
    void shouldAllowNullAmount() {
        Transaction t = new Transaction();
        t.setAmount(null);

        assertNull(t.getAmount());
    }
}