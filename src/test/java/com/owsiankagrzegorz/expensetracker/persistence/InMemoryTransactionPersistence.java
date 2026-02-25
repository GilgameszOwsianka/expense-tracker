package com.owsiankagrzegorz.expensetracker.persistence;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionPersistence implements TransactionPersistence {

    private List<Transaction> stored = new ArrayList<>();

    @Override
    public void save(Path path, List<Transaction> transactions) throws IOException {
        // path is ignored intentionally - this is a test stub
        stored = new ArrayList<>(transactions);
    }

    @Override
    public List<Transaction> load(Path path) throws IOException {
        // path is ignored intentionally - this is a test stub
        return new ArrayList<>(stored);
    }
}
