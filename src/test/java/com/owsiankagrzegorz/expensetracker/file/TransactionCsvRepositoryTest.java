package com.owsiankagrzegorz.expensetracker.file;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.CsvTransactionPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCsvRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSaveTransactionsToCsvFile() throws IOException {
        // given
        CsvTransactionPersistence repo = new CsvTransactionPersistence();
        Path filePath = tempDir.resolve("transactions.csv");

        List<Transaction> transactions = List.of(
                new Transaction(1L, bd("100.00"), "Jedzenie", LocalDate.of(2026, 2, 22), TransactionType.WYDATEK),
                new Transaction(2L, bd("200.00"), "Transport", LocalDate.of(2026, 2, 23), TransactionType.WYDATEK),
                new Transaction(3L, bd("300.00"), "Wypłata", LocalDate.of(2026, 2, 24), TransactionType.PRZYCHOD)
        );

        // when
        repo.save(filePath, transactions);

        // then
        assertTrue(Files.exists(filePath));

        List<String> lines = Files.readAllLines(filePath);
        assertFalse(lines.isEmpty());
        assertEquals("id,amount,category,date,type", lines.get(0));
        assertEquals(1 + transactions.size(), lines.size());

        assertEquals("1,100.00,Jedzenie,2026-02-22,WYDATEK", lines.get(1));
        assertEquals("2,200.00,Transport,2026-02-23,WYDATEK", lines.get(2));
        assertEquals("3,300.00,Wypłata,2026-02-24,PRZYCHOD", lines.get(3));
    }

    @Test
    void shouldLoadTransactionsFromCsvFile() throws IOException {
        // given
        CsvTransactionPersistence repo = new CsvTransactionPersistence();
        Path filePath = tempDir.resolve("transactions.csv");

        List<String> lines = List.of(
                "id,amount,category,date,type",
                "1,100.00,Jedzenie,2026-02-22,WYDATEK",
                "2,200.00,Transport,2026-02-23,WYDATEK",
                "3,300.00,Wypłata,2026-02-24,PRZYCHOD"
        );
        Files.write(filePath, lines);

        // when
        List<Transaction> transactions = repo.load(filePath);

        // then
        assertEquals(3, transactions.size());

        assertEquals(1L, transactions.get(0).getId());
        assertEquals(bd("100.00"), transactions.get(0).getAmount());

        assertEquals("Transport", transactions.get(1).getCategory());
        assertEquals(bd("200.00"), transactions.get(1).getAmount());

        assertEquals(TransactionType.PRZYCHOD, transactions.get(2).getType());
        assertEquals(bd("300.00"), transactions.get(2).getAmount());
    }

    private static BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}