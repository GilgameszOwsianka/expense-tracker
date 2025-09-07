package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.time.LocalDate;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Transaction transaction = new Transaction(150.0, "Jedzenie", LocalDate.now(), "WYDATEK");
        System.out.println(transaction);
    }
}
