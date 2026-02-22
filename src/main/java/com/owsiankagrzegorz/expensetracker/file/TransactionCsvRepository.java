package com.owsiankagrzegorz.expensetracker.file;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class TransactionCsvRepository {

    public void save(Path path, List<Transaction> transactions) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("id,amount,category,date,type");
            writer.newLine();

            for (Transaction t : transactions) {
                writer.write(toCsvLine(t));
                writer.newLine();
            }
        }
    }

    private String toCsvLine(Transaction t) {
        // Minimal CSV: assumes category/type do not contain commas/newlines.
        // I'll harden it later if needed.
        return t.getId() + ","
                + t.getAmount() + ","
                + t.getCategory() + ","
                + t.getDate() + ","
                + t.getType();
    }
}