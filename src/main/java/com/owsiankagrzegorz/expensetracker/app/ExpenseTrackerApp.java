package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTrackerApp {

    public static void main(String[] args) {
        TransactionListManager manager = new TransactionListManager();

        // TEMP seed data (for manual testing) - we'll clean this up in a later commit
        manager.addTransaction(new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), "WYDATEK"));
        manager.addTransaction(new Transaction(2L, 200.0, "Transport", LocalDate.now(), "WYDATEK"));
        manager.addTransaction(new Transaction(3L, 300.0, "Przychód", LocalDate.now(), "PRZYCHOD"));

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("\nAll transactions:");

                    if (manager.getAllTransactions().isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (Transaction t : manager.getAllTransactions()) {
                            System.out.println(t);
                        }
                    }
                }
                case 2 -> System.out.println("Add transaction not implemented yet.");
                case 3 -> {
                    long id = readLong(scanner, "Enter transaction ID to delete: ");
                    boolean removed = manager.removeTransactionById(id);

                    if (removed) {
                        System.out.println("Transaction removed.");
                    } else {
                        System.out.println("Transaction not found.");
                    }
                }
                case 4 -> System.out.println("TODO: filter transactions");
                case 0 -> {
                    System.out.println("Bye!");
                    running = false;
                }
                default -> System.out.println("Unknown option. Try again.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Expense Tracker ===");
        System.out.println("1) List all transactions");
        System.out.println("2) Add transaction (not implemented yet)");
        System.out.println("3) Delete transaction by id");
        System.out.println("4) Filter transactions");
        System.out.println("0) Exit");
        System.out.print("Choose option: ");
    }

    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    private static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}