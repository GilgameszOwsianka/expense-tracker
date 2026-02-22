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
                case 1 -> handleList(manager);
                case 2 -> handleAddTransaction(manager, scanner);
                case 3 -> handleDelete(manager, scanner);
                case 4 -> handleFilter(manager, scanner);
                case 5 -> handleSaveToCsv(manager);
                case 6 -> handleLoadFromCsv(manager);
                case 0 -> {
                    System.out.println("Bye!");
                    running = false;
                }
                default -> System.out.println("Unknown option. Try again.");
            }
        }

        scanner.close();
    }

    private static void handleFilter(TransactionListManager manager, Scanner scanner) {
        System.out.println("\nFilter transactions by type");

        String type = readTransactionTypeString(scanner, "Enter type (WYDATEK/PRZYCHOD): ");

        var filtered = manager.filterByType(type);

        if (filtered.isEmpty()) {
            System.out.println("No transactions found for type: " + type);
            return;
        }

        System.out.println("\nFiltered transactions:");
        for (Transaction t : filtered) {
            System.out.println(t);
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Expense Tracker ===");
        System.out.println("1) List all transactions");
        System.out.println("2) Add transaction");
        System.out.println("3) Delete transaction by id");
        System.out.println("4) Filter transactions");
        System.out.println("5) Save transactions to CSV");
        System.out.println("6) Load transactions from CSV");
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

    private static void handleList(TransactionListManager manager) {
        System.out.println("\nAll transactions:");

        if (manager.getAllTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction t : manager.getAllTransactions()) {
            System.out.println(t);
        }
    }

    private static void handleDelete(TransactionListManager manager, Scanner scanner) {
        long id = readLong(scanner, "Enter transaction ID to delete: ");
        boolean removed = manager.removeTransactionById(id);

        if (removed) {
            System.out.println("Transaction removed.");
        } else {
            System.out.println("Transaction not found.");
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

    private static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Amount must be greater than 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Value cannot be empty. Try again.");
        }
    }

    private static String readTransactionTypeString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("WYDATEK") || input.equals("PRZYCHOD")) {
                return input;
            }

            System.out.println("Invalid type. Enter WYDATEK or PRZYCHOD.");
        }
    }

    private static long nextTransactionId(TransactionListManager manager) {
        long maxId = 0L;
        for (Transaction t : manager.getAllTransactions()) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }

    private static void handleAddTransaction(TransactionListManager manager, Scanner scanner) {
        System.out.println("\nAdd new transaction");

        double amount = readPositiveDouble(scanner, "Amount: ");
        String category = readNonEmptyString(scanner, "Category: ");
        String type = readTransactionTypeString(scanner, "Type (WYDATEK/PRZYCHOD): ");

        long id = nextTransactionId(manager);
        Transaction transaction = new Transaction(id, amount, category, LocalDate.now(), type);

        manager.addTransaction(transaction);

        System.out.println("Transaction added with ID: " + id);
    }

    private static void handleSaveToCsv(TransactionListManager manager) {
        var repo = new com.owsiankagrzegorz.expensetracker.file.TransactionCsvRepository();
        java.nio.file.Path path = java.nio.file.Path.of("data", "transactions.csv");

        try {
            java.nio.file.Files.createDirectories(path.getParent());
            repo.save(path, manager.getAllTransactions());
            System.out.println("Saved to: " + path.toAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    private static void handleLoadFromCsv(TransactionListManager manager) {
        var repo = new com.owsiankagrzegorz.expensetracker.file.TransactionCsvRepository();
        java.nio.file.Path path = java.nio.file.Path.of("data", "transactions.csv");

        try {
            var loaded = repo.load(path);
            manager.clearTransactions();
            manager.addTransactions(loaded);

            System.out.println("Loaded " + loaded.size() + " transactions from: " + path.toAbsolutePath());
        } catch (java.nio.file.NoSuchFileException e) {
            System.out.println("File not found: " + path.toAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Error while loading file: " + e.getMessage());
        }
    }
}