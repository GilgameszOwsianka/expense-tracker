package com.owsiankagrzegorz.expensetracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TransactionTest {
    @Test
    void testTransactionCreation() {
        Transaction t = new Transaction(200.0, "Transport", LocalDate.of(2025, 9, 7), "WYDATEK");

        assertEquals(200.0, t.getAmount());
        assertEquals("Transport", t.getCategory());
        assertEquals(LocalDate.of(2025, 9, 7), t.getDate());
        assertEquals("WYDATEK", t.getType());
    }
}
