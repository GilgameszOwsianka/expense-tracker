package com.owsiankagrzegorz.expensetracker.app.io;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConsolePrinter {

    public void printMainMenu() {
        System.out.println();
        System.out.println("=== Expense Tracker ===");
        System.out.println("1) List all transactions");
        System.out.println("2) Add transaction");
        System.out.println("3) Delete transaction by id");
        System.out.println("4) Filter transactions");
        System.out.println("5) Save transactions to CSV");
        System.out.println("6) Load transactions from CSV");
        System.out.println("7) Query / Filter transactions");
        System.out.println("8) Reports");
        System.out.println("0) Exit");
        System.out.print("Choose option: ");
    }

    public void separator() {
        System.out.println("--------------------------------------------------");
    }

    public void info(String msg) {
        System.out.println(msg);
    }

    public void error(String msg) {
        System.out.println(msg);
    }

    public String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public void prompt(String msg) {
        System.out.print(msg);
    }

    public void blankLine() {
        System.out.println();
    }
}
