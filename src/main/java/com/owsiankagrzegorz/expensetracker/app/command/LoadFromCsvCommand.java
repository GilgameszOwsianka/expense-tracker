package com.owsiankagrzegorz.expensetracker.app.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class LoadFromCsvCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(LoadFromCsvCommand.class);
    private static final String DEFAULT_FILENAME = "transactions.csv";

    private final AppContext ctx;

    public LoadFromCsvCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().info("\nLoad transactions from CSV");
        ctx.printer().prompt("Enter CSV file path to load (default: " + DEFAULT_FILENAME + "): ");

        String input = ctx.input().readLineTrimmed();
        if (input.isBlank()) {
            input = DEFAULT_FILENAME;
        }

        Path path = Path.of(input);

        try {
            int loadedCount = ctx.service().load(path);
            log.info("Loaded transactions from CSV file: path={}, count={}", path, loadedCount);
            ctx.printer().info("Loaded " + loadedCount + " transactions from " + path);
        } catch (NoSuchFileException e) {
            log.warn("CSV file not found: path={}", path);
            ctx.printer().error("File not found: " + path);
        } catch (Exception e) {
            log.error("Failed to load transactions from CSV file: path={}", path, e);
            ctx.printer().error("Failed to load CSV: " + e.getMessage());
        }
    }
}