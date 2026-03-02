package com.owsiankagrzegorz.expensetracker.app.command;

public class DeleteTransactionCommand implements Command {

    private final AppContext ctx;

    public DeleteTransactionCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        long id = ctx.input().readLong("Enter transaction ID to delete: ");
        boolean removed = ctx.service().removeTransactionById(id);

        if (removed) {
            ctx.printer().info("Transaction removed.");
        } else {
            ctx.printer().info("Transaction not found.");
        }
    }
}