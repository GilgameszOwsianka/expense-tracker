package com.owsiankagrzegorz.expensetracker.app.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTransactionCommand implements Command {

    private final AppContext ctx;
    private static final Logger log = LoggerFactory.getLogger(DeleteTransactionCommand.class);

    public DeleteTransactionCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        long id = ctx.input().readLong("Enter transaction ID to delete: ");
        boolean removed = ctx.service().removeTransactionById(id);

        if (removed) {
            log.info("Deleted transaction: id={}", id);
            ctx.printer().info("Transaction removed.");
        } else {
            log.warn("Transaction not found for deletion: id={}", id);
            ctx.printer().info("Transaction not found.");
        }
    }
}