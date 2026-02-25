package com.owsiankagrzegorz.expensetracker.repository;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public boolean removeById(long id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    @Override
    public void replaceAll(List<Transaction> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);
    }

    @Override
    public void clear() {
        transactions.clear();
    }

    @Override
    public int size() {
        return transactions.size();
    }
}
