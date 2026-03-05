package com.owsiankagrzegorz.expensetracker.app.command;

import java.nio.file.Files;
import java.nio.file.Path;

public class SaveToCsvCommand implements Command {

    private static final String DEFAULT_FILENAME = "transactions.csv";

    private final AppContext ctx;

    public SaveToCsvCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().info("\nSave transactions to CSV");

        ctx.printer().prompt("Enter file path to save (default: " + DEFAULT_FILENAME + "): ");
        String input = ctx.input().readLineTrimmed();

        if (input.isBlank()) {
            input = DEFAULT_FILENAME;
        } else if (!input.toLowerCase().endsWith(".csv")) {
            input = input + ".csv";
        }

        Path path = Path.of(input);

        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            ctx.service().save(path);
            ctx.printer().info("Transactions saved to: " + path);
        } catch (Exception e) {
            ctx.printer().error("Failed to save CSV: " + e.getMessage());
        }
    }
}