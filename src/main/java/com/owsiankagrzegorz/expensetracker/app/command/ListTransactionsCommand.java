package com.owsiankagrzegorz.expensetracker.app.command;

public class ListTransactionsCommand implements Command {

    private final AppContext ctx;

    public ListTransactionsCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        var all = ctx.service().getAllTransactions();

        ctx.printer().info("\nAll transactions:");

        if (all.isEmpty()) {
            ctx.printer().info("No transactions yet.");
            return;
        }

        all.forEach(t -> ctx.printer().info(t.toString()));
    }
}