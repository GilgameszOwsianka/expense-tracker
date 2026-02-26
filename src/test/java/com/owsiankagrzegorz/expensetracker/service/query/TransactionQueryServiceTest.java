package com.owsiankagrzegorz.expensetracker.service.query;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionQueryServiceTest {

    private TransactionQueryService queryService;

    @BeforeEach
    void setUp() {
        TransactionRepository repo = new InMemoryTransactionRepository();
        queryService = new TransactionQueryService(repo);

        repo.add(new Transaction(1L, 100.0, "Jedzenie", LocalDate.of(2026, 1, 10), TransactionType.WYDATEK));
        repo.add(new Transaction(2L, 200.0, "Transport", LocalDate.of(2026, 1, 11), TransactionType.WYDATEK));
        repo.add(new Transaction(3L, 500.0, "Wypłata", LocalDate.of(2026, 1, 15), TransactionType.PRZYCHOD));
        repo.add(new Transaction(4L, 50.0, "Jedzenie", LocalDate.of(2026, 2, 1), TransactionType.WYDATEK));
    }

    @Test
    void shouldFilterByType() {
        var query = TransactionQuery.builder()
                .type(TransactionType.WYDATEK)
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(t -> t.getType() == TransactionType.WYDATEK));
    }

    @Test
    void shouldFilterByDateRangeInclusive() {
        var query = TransactionQuery.builder()
                .dateFrom(LocalDate.of(2026, 1, 11))
                .dateTo(LocalDate.of(2026, 1, 15))
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t ->
                !t.getDate().isBefore(LocalDate.of(2026, 1, 11)) &&
                        !t.getDate().isAfter(LocalDate.of(2026, 1, 15))
        ));
    }

    @Test
    void shouldFilterByCategoryCaseInsensitive() {
        var query = TransactionQuery.builder()
                .category("jEdZeNiE")
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getCategory().equalsIgnoreCase("Jedzenie")));
    }

    @Test
    void shouldSortByAmountDesc() {
        var query = TransactionQuery.builder()
                .sort(SortSpec.of(SortField.AMOUNT, SortDirection.DESC))
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(4, result.size());
        assertEquals(500.0, result.get(0).getAmount());
        assertEquals(50.0, result.get(3).getAmount());
    }

    @Test
    void shouldApplyLimitAfterSorting() {
        var query = TransactionQuery.builder()
                .sort(SortSpec.of(SortField.AMOUNT, SortDirection.DESC))
                .limit(2)
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(2, result.size());
        assertEquals(500.0, result.get(0).getAmount());
        assertEquals(200.0, result.get(1).getAmount());
    }

    @Test
    void shouldFilterByAmountRange() {
        var query = TransactionQuery.builder()
                .minAmount(60.0)
                .maxAmount(250.0)
                .build();

        List<Transaction> result = queryService.find(query);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getAmount() >= 60.0 && t.getAmount() <= 250.0));
    }
}
