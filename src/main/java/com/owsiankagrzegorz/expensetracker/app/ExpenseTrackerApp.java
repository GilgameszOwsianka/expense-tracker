package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.time.LocalDate;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        TransactionListManager manager = new TransactionListManager();
        manager.addTransaction(new Transaction(1L,100.0, "Jedzenie", LocalDate.now(), "WYDATEK"));
        manager.addTransaction(new Transaction(2L,200.0, "Transport", LocalDate.now(), "WYDATEK"));
        manager.addTransaction(new Transaction(3L,300.0, "Przychód", LocalDate.now(), "PRZYCHOD"));

        System.out.println("Wszystkie transakcje:");
        for (Transaction t : manager.getAllTransactions()) {
            System.out.println(t);
        }

        System.out.println("\nTylko wydatki:");
        for (Transaction t : manager.filterByType("WYDATEK")) {
            System.out.println(t);
        }
    }
}
