package com.owsiankagrzegorz.expensetracker.app.command;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class LoadFromCsvCommand implements Command {

    private final AppContext ctx;

    public LoadFromCsvCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        Path path = Path.of("data", "transactions.csv");

        try {
            int loadedCount = ctx.service().load(path);
            ctx.printer().info("Loaded " + loadedCount + " transactions from: " + path.toAbsolutePath());
        } catch (NoSuchFileException e) {
            ctx.printer().error("File not found: " + path.toAbsolutePath());
        } catch (Exception e) {
            ctx.printer().error("Error while loading file: " + e.getMessage());
        }
    }
}