package com.owsiankagrzegorz.expensetracker.file;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
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
        TransactionCsvRepository repo = new TransactionCsvRepository();
        Path filePath = tempDir.resolve("transactions.csv");

        List<Transaction> transactions = List.of(
                new Transaction(1L, 100.0, "Jedzenie", LocalDate.of(2026, 2, 22), "WYDATEK"),
                new Transaction(2L, 200.0, "Transport", LocalDate.of(2026, 2, 23), "WYDATEK"),
                new Transaction(3L, 300.0, "Wypłata", LocalDate.of(2026, 2, 24), "PRZYCHOD")
        );

        // when
        repo.save(filePath, transactions);

        // then
        assertTrue(Files.exists(filePath));

        List<String> lines = Files.readAllLines(filePath);
        assertFalse(lines.isEmpty());
        assertEquals("id,amount,category,date,type", lines.get(0));
        assertEquals(1 + transactions.size(), lines.size());

        assertEquals("1,100.0,Jedzenie,2026-02-22,WYDATEK", lines.get(1));
        assertEquals("2,200.0,Transport,2026-02-23,WYDATEK", lines.get(2));
        assertEquals("3,300.0,Wypłata,2026-02-24,PRZYCHOD", lines.get(3));
    }
}
