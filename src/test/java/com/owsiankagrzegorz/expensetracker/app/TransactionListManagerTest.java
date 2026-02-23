package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionListManagerTest {

    private TransactionListManager manager;

    @BeforeEach
    void setUp() {
        manager = new TransactionListManager();
    }

    @Test
    void shouldAddTransaction() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        manager.addTransaction(t1);

        List<Transaction> all = manager.getAllTransactions();
        assertEquals(1, all.size());
        assertEquals("Jedzenie", all.get(0).getCategory());
        assertEquals(1L, all.get(0).getId());
    }

    @Test
    void shouldReturnAllTransactionsInInsertOrder() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);

        manager.addTransaction(t1);
        manager.addTransaction(t2);

        List<Transaction> all = manager.getAllTransactions();
        assertEquals(2, all.size());
        assertEquals("Jedzenie", all.get(0).getCategory());
        assertEquals("Transport", all.get(1).getCategory());
    }

    @Test
    void shouldRemoveTransactionByIdWhenExists() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);

        manager.addTransaction(t1);
        manager.addTransaction(t2);

        boolean removed = manager.removeTransactionById(2L);

        assertTrue(removed);
        List<Transaction> all = manager.getAllTransactions();
        assertEquals(1, all.size());
        assertEquals(1L, all.get(0).getId());
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistingId() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        manager.addTransaction(t1);

        boolean removed = manager.removeTransactionById(999L);

        assertFalse(removed);
        assertEquals(1, manager.getAllTransactions().size());
    }

    @Test
    void shouldFilterByType() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t3 = new Transaction(3L, 500.0, "Wypłata", LocalDate.now(), TransactionType.PRZYCHOD);

        manager.addTransaction(t1);
        manager.addTransaction(t2);
        manager.addTransaction(t3);

        List<Transaction> wydatki = manager.filterByType(TransactionType.WYDATEK);
        assertEquals(2, wydatki.size());

        List<Transaction> przychody = manager.filterByType(TransactionType.PRZYCHOD);
        assertEquals(1, przychody.size());
        assertEquals("Wypłata", przychody.get(0).getCategory());
        assertEquals(3L, przychody.get(0).getId());
    }

    @Test
    void shouldClearAndAddTransactionsInBulk() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);

        manager.addTransaction(t1);
        assertEquals(1, manager.getSize());

        manager.clearTransactions();
        assertEquals(0, manager.getSize());

        manager.addTransactions(List.of(t1, t2));
        assertEquals(2, manager.getSize());
    }
}