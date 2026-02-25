package com.owsiankagrzegorz.expensetracker.repository;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    void add(Transaction transaction);

    List<Transaction> findAll();

    boolean removeById(long id);

    void replaceAll(List<Transaction> transactions);

    void clear();

    int size();
}
