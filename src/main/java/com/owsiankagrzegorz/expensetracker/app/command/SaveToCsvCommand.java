package com.owsiankagrzegorz.expensetracker.app.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class SaveToCsvCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(SaveToCsvCommand.class);
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
            log.info("Saved transactions to CSV file: path={}", path);
            ctx.printer().info("Transactions saved to: " + path);
        } catch (Exception e) {
            log.error("Failed to save transactions to CSV file: path={}", path, e);
            ctx.printer().error("Failed to save CSV: " + e.getMessage());
        }
    }
}