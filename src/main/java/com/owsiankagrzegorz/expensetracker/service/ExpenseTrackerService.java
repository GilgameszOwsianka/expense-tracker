package com.owsiankagrzegorz.expensetracker.service;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerService {
    private final TransactionRepository repository;

    public ExpenseTrackerService(TransactionRepository repository) {
        this.repository = repository;
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
}
