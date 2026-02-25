package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.CsvTransactionPersistence;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.ExpenseTrackerService;

import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTrackerApp {

    public static void main(String[] args) {
        ExpenseTrackerService service = new ExpenseTrackerService(
                new InMemoryTransactionRepository(),
                new CsvTransactionPersistence()
        );

        // TEMP seed data (for manual testing) - we'll clean this up in a later commit
        service.addTransaction(new Transaction(1L, 100.0, "Jedzenie", LocalDate.now(), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(2L, 200.0, "Transport", LocalDate.now(), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(3L, 300.0, "Przychód", LocalDate.now(), TransactionType.PRZYCHOD));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1 -> handleList(service);
                case 2 -> handleAddTransaction(service, scanner);
                case 3 -> handleDelete(service, scanner);
                case 4 -> handleFilter(service, scanner);
                case 5 -> handleSaveToCsv(service);
                case 6 -> handleLoadFromCsv(service);
                case 0 -> {
                    System.out.println("Bye!");
                    running = false;
                }
                default -> System.out.println("Unknown option.\nTry again.");
            }
        }

        scanner.close();
    }

    private static void handleFilter(ExpenseTrackerService service, Scanner scanner) {
        System.out.println("\nFilter transactions by type");
        TransactionType type = readTransactionType(scanner, "Enter type (WYDATEK/PRZYCHOD): ");

        var filtered = service.filterByType(type);

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
                System.out.print("Invalid input.\nEnter a number: ");
            }
        }
    }

    private static void handleList(ExpenseTrackerService service) {
        System.out.println("\nAll transactions:");

        if (service.getAllTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction t : service.getAllTransactions()) {
            System.out.println(t);
        }
    }

    private static void handleDelete(ExpenseTrackerService service, Scanner scanner) {
        long id = readLong(scanner, "Enter transaction ID to delete: ");
        boolean removed = service.removeTransactionById(id);

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
                System.out.println("Invalid number.\nTry again.");
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
                System.out.println("Invalid number.\nTry again.");
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
            System.out.println("Value cannot be empty.\nTry again.");
        }
    }

    private static TransactionType readTransactionType(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return TransactionType.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type.\nEnter WYDATEK or PRZYCHOD.");
            }
        }
    }

    private static void handleAddTransaction(ExpenseTrackerService service, Scanner scanner) {
        System.out.println("\nAdd new transaction");

        double amount = readPositiveDouble(scanner, "Amount: ");
        String category = readNonEmptyString(scanner, "Category: ");
        TransactionType type = readTransactionType(scanner, "Type (WYDATEK/PRZYCHOD): ");

        long id = service.nextTransactionId();

        Transaction transaction = new Transaction(id, amount, category, LocalDate.now(), type);
        service.addTransaction(transaction);

        System.out.println("Transaction added with ID: " + id);
    }

    private static void handleSaveToCsv(ExpenseTrackerService service) {
        java.nio.file.Path path = java.nio.file.Path.of("data", "transactions.csv");

        try {
            java.nio.file.Files.createDirectories(path.getParent());
            service.save(path);
            System.out.println("Saved to: " + path.toAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    private static void handleLoadFromCsv(ExpenseTrackerService service) {
        java.nio.file.Path path = java.nio.file.Path.of("data", "transactions.csv");

        try {
            int loadedCount = service.load(path);
            System.out.println("Loaded " + loadedCount + " transactions from: " + path.toAbsolutePath());
        } catch (java.nio.file.NoSuchFileException e) {
            System.out.println("File not found: " + path.toAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Error while loading file: " + e.getMessage());
        }
    }
}