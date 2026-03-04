package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddTransactionCommand implements Command {

    private final AppContext ctx;

    public AddTransactionCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().info("\nAdd new transaction");

        BigDecimal amount = ctx.input().readPositiveBigDecimal("Amount: ");

        String category = ctx.input().readNonEmptyString("Category: ");
        var type = ctx.input().readTransactionType("Type (WYDATEK/PRZYCHOD): ");

        long id = ctx.service().nextTransactionId();
        Transaction transaction = new Transaction(id, amount, category, LocalDate.now(), type);
        ctx.service().addTransaction(transaction);

        ctx.printer().info("Transaction added with ID: " + id);
    }
}