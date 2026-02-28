package com.owsiankagrzegorz.expensetracker.app.io;

import com.owsiankagrzegorz.expensetracker.model.TransactionType;

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
