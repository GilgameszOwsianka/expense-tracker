package com.owsiankagrzegorz.expensetracker.service;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.TransactionPersistence;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerService {
    private final TransactionRepository repository;
    private final TransactionPersistence persistence;

    public ExpenseTrackerService(TransactionRepository repository, TransactionPersistence persistence) {
        this.repository = repository;
        this.persistence = persistence;
    }

    public void addTransaction(Transaction transaction) {
        repository.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public boolean removeTransactionById(long id) {
        return repository.removeById(id);
    }

    public List<Transaction> filterByType(TransactionType type) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : repository.findAll()) {
            if (t.getType() == type) {
                result.add(t);
            }
        }
        return result;
    }

    public void clearTransactions() {
        repository.clear();
    }

    public int getSize() {
        return repository.size();
    }

    public void replaceAllTransactions(List<Transaction> transactions) {
        repository.replaceAll(transactions);
    }

    public long nextTransactionId() {
        long maxId = 0L;
        for (Transaction t : repository.findAll()) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }

    public void save(Path path) throws IOException {
        persistence.save(path, repository.findAll());
    }

    public int load(Path path) throws IOException {
        List<Transaction> loaded = persistence.load(path);
        repository.replaceAll(loaded);
        return loaded.size();
    }
}
