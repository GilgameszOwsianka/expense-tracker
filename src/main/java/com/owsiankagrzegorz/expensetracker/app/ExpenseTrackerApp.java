package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.app.command.*;
import com.owsiankagrzegorz.expensetracker.app.io.ConsolePrinter;
import com.owsiankagrzegorz.expensetracker.app.io.InputReader;
import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.CsvTransactionPersistence;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.ExpenseTrackerService;
import com.owsiankagrzegorz.expensetracker.service.query.*;
import com.owsiankagrzegorz.expensetracker.service.report.TransactionReportService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class ExpenseTrackerApp {

    public static void main(String[] args) {

        var repository = new InMemoryTransactionRepository();
        var persistence = new CsvTransactionPersistence();
        var service = new ExpenseTrackerService(repository, persistence);
        var queryService = new TransactionQueryService(repository);
        var reportService = new TransactionReportService(repository);

        // TEMP seed data (for manual testing) - we'll clean this up in a later commit
        service.addTransaction(new Transaction(1L, 100.0, "Jedzenie", LocalDate.of(2026, 1, 1), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(2L, 200.0, "Transport", LocalDate.of(2026, 1, 1), TransactionType.WYDATEK));
        service.addTransaction(new Transaction(3L, 300.0, "Przychód", LocalDate.of(2026, 1, 1), TransactionType.PRZYCHOD));

        Scanner scanner = new Scanner(System.in);
        InputReader input = new InputReader(scanner);
        ConsolePrinter printer = new ConsolePrinter();

        AppContext ctx = new AppContext(service, queryService, input, printer);
        ExitSignal exitSignal = new ExitSignal();
        var exitCommand = new ExitCommand(ctx, exitSignal);
        var unknownCommand = new UnknownOptionCommand(ctx);

        CommandRegistry registry = new CommandRegistry(unknownCommand);
        registry.register(1, new ListTransactionsCommand(ctx));
        registry.register(2, new AddTransactionCommand(ctx));
        registry.register(3, new DeleteTransactionCommand(ctx));
        registry.register(4, new FilterByTypeCommand(ctx));
        registry.register(5, new SaveToCsvCommand(ctx));
        registry.register(6, new LoadFromCsvCommand(ctx));
        registry.register(7, new QueryTransactionsCommand(ctx));
        registry.register(0, exitCommand);

        while (!exitSignal.isExitRequested()) {
            printer.printMainMenu();
            int choice = input.readMenuChoice();

            if (choice >= 0 && choice <= 7) {
                registry.get(choice).execute();
            } else {
                switch (choice) {
                    case 8 -> handleReports(reportService, scanner);
                    default -> registry.get(choice).execute();
                }
            }
        }

        input.close();
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

    private static void printSeparator() {
        System.out.println("--------------------------------------------------");
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

    private static void handleQuery(TransactionQueryService queryService, Scanner scanner) {
        System.out.println("\n--- Query / Filter ---");

        TransactionQuery.Builder builder = TransactionQuery.builder();

        // 1) Type (optional, validated)
        TransactionType type = readOptionalTransactionType(scanner, "Filter by type (WYDATEK/PRZYCHOD or empty): ");
        if (type != null) {
            builder.type(type);
        }

        // 2) Date range (optional, validated + early feedback)
        LocalDate dateFrom;
        LocalDate dateTo;
        while (true) {
            dateFrom = readOptionalDate(scanner, "Date from (yyyy-MM-dd or empty): ");
            dateTo = readOptionalDate(scanner, "Date to (yyyy-MM-dd or empty): ");

            if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
                System.out.println("Invalid range: dateFrom must be <= dateTo. Try again.");
                continue;
            }
            break;
        }

        if (dateFrom != null) {
            builder.dateFrom(dateFrom);
        }
        if (dateTo != null) {
            builder.dateTo(dateTo);
        }

        // 3) Category (optional)
        System.out.print("Category (or empty): ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            builder.category(category);
        }

        // 4) Amount range (optional, validated + early feedback)
        Double[] range = readOptionalAmountRange(scanner);
        Double minAmount = range[0];
        Double maxAmount = range[1];

        if (minAmount != null) {
            builder.minAmount(minAmount);
        }
        if (maxAmount != null) {
            builder.maxAmount(maxAmount);
        }

        // 5) Sorting (optional, validated)
        SortField sortField = readOptionalSortField(scanner, "Sort field (DATE/AMOUNT/CATEGORY/TYPE or empty): ");
        if (sortField != null) {
            SortDirection direction = readOptionalSortDirection(scanner, "Sort direction (ASC/DESC or empty=ASC): ");
            if (direction == null) {
                direction = SortDirection.ASC;
            }
            builder.sort(SortSpec.of(sortField, direction));
        }

        // 6) Limit (optional, validated)
        Integer limit = readOptionalInt(scanner, "Limit results (or empty): ");
        if (limit != null) {
            builder.limit(limit);
        }

        // 7) Build query (last-line validation, no crash)
        TransactionQuery query;
        try {
            query = builder.build();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid query: " + e.getMessage());
            return;
        }

        var results = queryService.find(query);

        if (results.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nResults (" + results.size() + "):");
        results.forEach(System.out::println);
    }

    private static void handleReports(TransactionReportService reportService, Scanner scanner) {
        System.out.println("\n--- Reports ---");
        System.out.println("1) Monthly summary");
        System.out.println("2) Period summary");
        System.out.println("3) Category breakdown (optional type)");

        System.out.print("Choose option: ");
        String option = scanner.nextLine().trim();

        switch (option) {
            case "1" -> {

                YearMonth month = readOptionalYearMonth(scanner, "Enter month (yyyy-MM): ");
                if (month == null) {
                    System.out.println("Month is required");
                    return;
                }
                var summary = reportService.reportMonthly(month);

                printSeparator();
                System.out.printf("%-20s %10s%n", "Month:", summary.getMonth());
                printSeparator();
                System.out.printf("%-20s %10s%n", "Income:", formatAmount(summary.getIncomeTotal()));
                System.out.printf("%-20s %10s%n", "Expense:", formatAmount(summary.getExpenseTotal()));
                printSeparator();
                System.out.printf("%-20s %10s%n", "Balance:", formatAmount(summary.getBalance()));
                printSeparator();
            }
            case "2" -> {
                LocalDate from = readOptionalDate(scanner, "From (yyyy-MM-dd): ");
                if (from == null) {
                    System.out.println("From date is required.");
                    return;
                }

                LocalDate to = readOptionalDate(scanner, "To (yyyy-MM-dd): ");
                if (to == null) {
                    System.out.println("To date is required.");
                    return;
                }

                var summary = reportService.reportPeriod(from, to);

                printSeparator();
                System.out.printf("%-20s %10s%n", "Income:", formatAmount(summary.getIncomeTotal()));
                System.out.printf("%-20s %10s%n", "Expense:", formatAmount(summary.getExpenseTotal()));
                printSeparator();
                System.out.printf("%-20s %10s%n", "Balance:", formatAmount(summary.getBalance()));
                printSeparator();
            }
            case "3" -> {
                LocalDate from = readOptionalDate(scanner, "From (yyyy-MM-dd): ");
                if (from == null) {
                    System.out.println("From date is required.");
                    return;
                }

                LocalDate to = readOptionalDate(scanner, "To (yyyy-MM-dd): ");
                if (to == null) {
                    System.out.println("To date is required.");
                    return;
                }

                // Walidacja zakresu dat
                if (from.isAfter(to)) {
                    System.out.println("Invalid range: From date must be before or equal To date.");
                    return;
                }

                // Bezpieczne wczytanie typu (bez crasha)
                TransactionType type = readOptionalTransactionType(
                        scanner,
                        "Type (WYDATEK/PRZYCHOD or empty for all): "
                );

                var breakdown = reportService.reportByCategory(from, to, type);

                if (breakdown.getTotalsByCategory().isEmpty()) {
                    System.out.println("No data for selected criteria.");
                    return;
                }

                printSeparator();
                System.out.printf("%-25s %10s%n", "Category", "Total");
                printSeparator();

                breakdown.getTotalsByCategory()
                        .forEach((category, total) ->
                                System.out.printf("%-25s %10s%n", category, formatAmount(total))
                        );

                printSeparator();
            }
            default -> System.out.println("Unknown option.");
        }
    }

    private static LocalDate readOptionalDate(Scanner scanner, String prompt) {
        while(true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date format. Expected yyyy-MM-dd.");
            }
        }
    }

    private static YearMonth readOptionalYearMonth (Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return YearMonth.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid month format. Expected yyyy-MM.");
            }
        }
    }

    private static Double readOptionalDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static Integer readOptionalInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static SortField readOptionalSortField(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return SortField.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sort field. Allowed: DATE, AMOUNT, CATEGORY, TYPE.");
            }
        }
    }

    private static SortDirection readOptionalSortDirection(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return SortDirection.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sort direction. Allowed: ASC, DESC.");
            }
        }
    }

    private static TransactionType readOptionalTransactionType(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return TransactionType.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Allowed: WYDATEK, PRZYCHOD.");
            }
        }
    }

    private static Double[] readOptionalAmountRange(Scanner scanner) {
        while (true) {
            Double min = readOptionalDouble(scanner, "Min amount (or empty): ");
            Double max = readOptionalDouble(scanner, "Max amount (or empty): ");

            if (min != null && max != null && min > max) {
                System.out.println("Invalid range: minAmount must be <= maxAmount. Try again.");
                continue;
            }

            return new Double[]{min, max};
        }
    }

    private static String formatAmount(double amount) {
        return String.format("%10.2f", amount);
    }
}