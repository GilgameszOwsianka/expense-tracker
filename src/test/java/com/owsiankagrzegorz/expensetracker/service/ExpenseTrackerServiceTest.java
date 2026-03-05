package com.owsiankagrzegorz.expensetracker.service;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.InMemoryTransactionPersistence;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTrackerServiceTest {

    private ExpenseTrackerService service;

    @BeforeEach
    void setUp() {
        service = new ExpenseTrackerService(
                new InMemoryTransactionRepository(),
                new InMemoryTransactionPersistence()
        );
    }

    @Test
    void shouldAddAndListTransactions() {
        Transaction t1 = new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);

        List<Transaction> all = service.getAllTransactions();
        assertEquals(1, all.size());
        assertEquals(1L, all.get(0).getId());
        assertEquals("Jedzenie", all.get(0).getCategory());
        assertEquals(bd("100.00"), all.get(0).getAmount());
    }

    @Test
    void shouldRemoveTransactionById() {
        Transaction t1 = new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, bd("200.00"), "Transport", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);
        service.addTransaction(t2);

        assertTrue(service.removeTransactionById(2L));
        assertEquals(1, service.getSize());
        assertEquals(1L, service.getAllTransactions().get(0).getId());
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistingId() {
        Transaction t1 = new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);

        assertFalse(service.removeTransactionById(999L));
        assertEquals(1, service.getSize());
    }

    @Test
    void shouldFilterByType() {
        Transaction t1 = new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, bd("200.00"), "Transport", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t3 = new Transaction(3L, bd("500.00"), "Wypłata", LocalDate.now(), TransactionType.PRZYCHOD);

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
        Transaction t1 = new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK);
        Transaction t2 = new Transaction(2L, bd("200.00"), "Transport", LocalDate.now(), TransactionType.WYDATEK);

        service.addTransaction(t1);
        assertEquals(1, service.getSize());

        service.replaceAllTransactions(List.of(t1, t2));
        assertEquals(2, service.getSize());
    }

    @Test
    void shouldGenerateNextTransactionId() {
        assertEquals(1L, service.nextTransactionId());

        service.addTransaction(new Transaction(10L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(2L, bd("200.00"), "Transport", LocalDate.now(), TransactionType.WYDATEK));

        assertEquals(11L, service.nextTransactionId());
    }

    @Test
    void shouldSaveAndLoadUsingPersistence() throws Exception {
        var persistence = new InMemoryTransactionPersistence();

        // --- First service instance ---
        var repo1 = new InMemoryTransactionRepository();
        var service1 = new ExpenseTrackerService(repo1, persistence);

        service1.addTransaction(new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.now(), TransactionType.WYDATEK));
        service1.addTransaction(new Transaction(2L, bd("300.00"), "Wypłata", LocalDate.now(), TransactionType.PRZYCHOD));

        java.nio.file.Path path = java.nio.file.Path.of("ignored.csv");
        service1.save(path);

        // --- Simulate application restart ---
        var repo2 = new InMemoryTransactionRepository();
        var service2 = new ExpenseTrackerService(repo2, persistence);

        int loaded = service2.load(path);
        assertEquals(2, loaded);
        assertEquals(2, service2.getSize());

        var all = service2.getAllTransactions();
        assertEquals(1L, all.get(0).getId());
        assertEquals(2L, all.get(1).getId());
    }

    // 2.4-5: flagship money correctness test
    @Test
    void shouldHandleMoneyWithoutFloatingPointErrors() {
        service.addTransaction(new Transaction(1L, bd("0.10"), "Test", LocalDate.now(), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(2L, bd("0.20"), "Test", LocalDate.now(), TransactionType.WYDATEK));

        BigDecimal sum = service.getAllTransactions().stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(bd("0.30"), sum);
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}