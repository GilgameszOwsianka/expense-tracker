package com.owsiankagrzegorz.expensetracker.service;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTrackerServiceTest {

    private ExpenseTrackerService service;

    @BeforeEach
    void setUp() {
        service = new ExpenseTrackerService(new InMemoryTransactionRepository());
    }

    @Test
    void shouldAddAndListTransactions() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);

        List<Transaction> all = service.getAllTransactions();
        assertEquals(1, all.size());
        assertEquals(1L, all.get(0).getId());
        assertEquals("Jedzenie", all.get(0).getCategory());
    }

    @Test
    void shouldRemoveTransactionById() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);
        service.addTransaction(t2);

        assertTrue(service.removeTransactionById(2L));
        assertEquals(1, service.getSize());
        assertEquals(1L, service.getAllTransactions().get(0).getId());
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistingId() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);

        assertFalse(service.removeTransactionById(999L));
        assertEquals(1, service.getSize());
    }

    @Test
    void shouldFilterByType() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t3 = new Transaction(3L, 500.0, "Wypłata", LocalDate.now(), TransactionType.PRZYCHOD);

        service.addTransaction(t1);
        service.addTransaction(t2);
        service.addTransaction(t3);

        List<Transaction> wydatki = service.filterByType(TransactionType.WYDATEK);
        assertEquals(2, wydatki.size());

        List<Transaction> przychody = service.filterByType(TransactionType.PRZYCHOD);
        assertEquals(1, przychody.size());
        assertEquals(3L, przychody.get(0).getId());
    }

    @Test
    void shouldReplaceAllTransactions() {
        Transaction t1 = new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);
        assertEquals(1, service.getSize());

        service.replaceAllTransactions(List.of(t1, t2));
        assertEquals(2, service.getSize());
    }

    @Test
    void shouldGenerateNextTransactionId() {
        assertEquals(1L, service.nextTransactionId());

        service.addTransaction(new Transaction(10L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK));

        assertEquals(11L, service.nextTransactionId());
    }
}
