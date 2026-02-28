package com.owsiankagrzegorz.expensetracker.app.command;

public class FilterByTypeCommand implements Command {

    private final AppContext ctx;

    public FilterByTypeCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().info("\nFilter transactions by type");
        var type = ctx.input().readTransactionType("Enter type (WYDATEK/PRZYCHOD): ");

        var filtered = ctx.service().filterByType(type);

        if (filtered.isEmpty()) {
            ctx.printer().info("No transactions found for type: " + type);
            return;
        }

        ctx.printer().info("\nFiltered transactions:");
        filtered.forEach(t -> ctx.printer().info(t.toString()));
    }
}