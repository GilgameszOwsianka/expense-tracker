package com.owsiankagrzegorz.expensetracker.persistence;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvTransactionPersistence implements TransactionPersistence {

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

    public List<Transaction> load(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        List<Transaction> transactions = new java.util.ArrayList<>();

        // Pomijamy nagłówek (linia 0)
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.isBlank()) {
                transactions.add(fromCsvLine(line));
            }
        }

        return transactions;
    }

    private Transaction fromCsvLine(String line) {
        String[] parts = line.split(",");

        long id = Long.parseLong(parts[0]);
        double amount = Double.parseDouble(parts[1]);
        String category = parts[2];
        java.time.LocalDate date = java.time.LocalDate.parse(parts[3]);
        TransactionType type = TransactionType.fromString(parts[4]);

        return new Transaction(id, amount, category, date, type);
    }

    private String toCsvLine(Transaction t) {
        // Minimal CSV: assumes category/type do not contain commas/newlines.
        // I'll harden it later if needed.
        return t.getId() + ","
                + t.getAmount() + ","
                + t.getCategory() + ","
                + t.getDate() + ","
                + t.getType().name();
    }
}