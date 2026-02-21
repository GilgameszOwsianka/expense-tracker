package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.util.Arrays;

public class TransactionArrayManager {
    private final Transaction[] transactions;
    private int size = 0;

    public TransactionArrayManager (int capacity) {
        this.transactions = new Transaction[capacity];
    }

    public boolean addTransaction (Transaction t) {
        if (size >= transactions.length) {
            return false; //tablica pełna
        }
        transactions[size++] = t;
        return true;
    }

    //Zwraca kopię aktywnej części tablicy [0...size-1]
    public Transaction[] getAllTransactions() {
        return Arrays.copyOf(transactions, size);
    }

    //Proste filtrowanie po typie ("WYDATEK" lub "PRZYCHÓD")
    public Transaction[] filterByType(String type) {
        Transaction[] temp = new Transaction[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (transactions[i] != null && type.equals(transactions[i].getType())) {
                temp[index++] = transactions[i];
            }
        }
        return Arrays.copyOf(temp, index);
    }

    public int getSize() {
        return size;
    }
}