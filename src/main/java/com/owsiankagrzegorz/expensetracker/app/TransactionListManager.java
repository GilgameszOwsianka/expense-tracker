package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionListManager {

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public boolean removeTransactionById(long id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    public List<Transaction> filterByType(TransactionType type) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType() == type) {
                result.add(t);
            }
        }
        return result;
    }

    public int getSize() {
        return transactions.size();
    }

    public void clearTransactions() {
        transactions.clear();
    }

    public void addTransactions(List<Transaction> transactionsToAdd) {
        transactions.addAll(transactionsToAdd);
    }
}