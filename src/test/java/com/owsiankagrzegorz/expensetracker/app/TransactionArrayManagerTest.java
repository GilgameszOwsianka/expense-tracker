package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionArrayManagerTest {

    private TransactionArrayManager manager;

    @BeforeEach
    void setUp() {
        manager = new TransactionArrayManager(5); // domyślna pojemność na start
    }

    @Test
    void testAddTransaction() {
        Transaction t1 = new Transaction(1L,100.0, "Jedzenie", LocalDate.now(), "WYDATEK");
        Transaction t2 = new Transaction(2L,200.0, "Transport", LocalDate.now(), "WYDATEK");

        assertTrue(manager.addTransaction(t1));
        assertTrue(manager.addTransaction(t2));
        assertEquals(2, manager.getSize());

        // Spróbuj dodać więcej niż pojemność
        manager = new TransactionArrayManager(2);
        assertTrue(manager.addTransaction(t1));
        assertTrue(manager.addTransaction(t2));
        Transaction t3 = new Transaction(3L,300.0, "Extra", LocalDate.now(), "PRZYCHOD");
        assertFalse(manager.addTransaction(t3));
    }

    @Test
    void testGetAllTransactions() {
        Transaction t1 = new Transaction(1L,100.0, "Jedzenie", LocalDate.now(), "WYDATEK");
        Transaction t2 = new Transaction(2L,200.0, "Transport", LocalDate.now(), "WYDATEK");

        manager.addTransaction(t1);
        manager.addTransaction(t2);

        Transaction[] all = manager.getAllTransactions();
        assertEquals(2, all.length);
        assertEquals("Jedzenie", all[0].getCategory());
        assertEquals("Transport", all[1].getCategory());
    }

    @Test
    void testFilterByType() {
        Transaction t1 = new Transaction(1L,100.0, "Jedzenie", LocalDate.now(), "WYDATEK");
        Transaction t2 = new Transaction(2L,200.0, "Transport", LocalDate.now(), "WYDATEK");
        Transaction t3 = new Transaction(3L,500.0, "Wypłata", LocalDate.now(), "PRZYCHOD");

        manager.addTransaction(t1);
        manager.addTransaction(t2);
        manager.addTransaction(t3);

        Transaction[] wydatki = manager.filterByType("WYDATEK");
        assertEquals(2, wydatki.length);

        Transaction[] przychody = manager.filterByType("PRZYCHOD");
        assertEquals(1, przychody.length);
        assertEquals("Wypłata", przychody[0].getCategory());
    }
}
