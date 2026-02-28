package com.owsiankagrzegorz.expensetracker.app.command;

import java.nio.file.Files;
import java.nio.file.Path;

public class SaveToCsvCommand implements Command {

    private final AppContext ctx;

    public SaveToCsvCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        Path path = Path.of("data", "transactions.csv");

        try {
            Files.createDirectories(path.getParent());
            ctx.service().save(path);
            ctx.printer().info("Saved to: " + path.toAbsolutePath());
        } catch (Exception e) {
            ctx.printer().error("Error while saving file: " + e.getMessage());
        }
    }
}