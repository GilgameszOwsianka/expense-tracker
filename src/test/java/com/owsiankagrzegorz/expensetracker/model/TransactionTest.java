package com.owsiankagrzegorz.expensetracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TransactionTest {

    LocalDate date = LocalDate.of(2025, 9, 7);
    @Test
    void shouldCreateTransactionWithGivenValues() {
        Transaction t = new Transaction(1L, 200.0, "Transport", LocalDate.of(2025, 9, 7), "WYDATEK");

        assertEquals(1L, t.getId());
        assertEquals(200.0, t.getAmount());
        assertEquals("Transport", t.getCategory());
        assertEquals(date, t.getDate());
        assertEquals("WYDATEK", t.getType());
    }
}
