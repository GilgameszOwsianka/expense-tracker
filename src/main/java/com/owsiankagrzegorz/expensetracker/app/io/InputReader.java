package com.owsiankagrzegorz.expensetracker.app.io;

import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.service.query.SortDirection;
import com.owsiankagrzegorz.expensetracker.service.query.SortField;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class InputReader implements AutoCloseable{

    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readMenuChoice() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input.\nEnter a number: ");
            }
        }
    }

    public long readLong(String prompt) {
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

    public double readPositiveDouble(String prompt) {
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

    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Value cannot be empty.\nTry again.");
        }
    }

    public TransactionType readTransactionType(String prompt) {
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

    public String readLineTrimmed() {
        return scanner.nextLine().trim();
    }

    public LocalDate readOptionalDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;

            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date format. Expected yyyy-MM-dd.");
            }
        }
    }

    public YearMonth readOptionalYearMonth(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;

            try {
                return YearMonth.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid month format. Expected yyyy-MM.");
            }
        }
    }

    public SortField readOptionalSortField(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;

            try {
                return SortField.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sort field. Allowed: DATE, AMOUNT, CATEGORY, TYPE.");
            }
        }
    }

    public SortDirection readOptionalSortDirection(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;

            try {
                return SortDirection.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sort direction. Allowed: ASC, DESC.");
            }
        }
    }

    public TransactionType readOptionalTransactionType(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;

            try {
                return TransactionType.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Allowed: WYDATEK, PRZYCHOD.");
            }
        }
    }

    public Double[] readOptionalAmountRange() {
        while (true) {
            Double min = readOptionalDouble("Min amount (or empty): ");
            Double max = readOptionalDouble("Max amount (or empty): ");

            if (min != null && max != null && min > max) {
                System.out.println("Invalid range: minAmount must be <= maxAmount. Try again.");
                continue;
            }

            return new Double[]{min, max};
        }
    }

    @Override
    public void close() {
        scanner.close();
    }

    public Double readOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.\nTry again.");
            }
        }
    }

    public Integer readOptionalInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.\nTry again.");
            }
        }
    }
}
