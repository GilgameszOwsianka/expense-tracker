package com.owsiankagrzegorz.expensetracker.persistence;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface TransactionPersistence {

    void save(Path path, List<Transaction> transactions) throws IOException;

    List<Transaction> load(Path path) throws IOException;
}
