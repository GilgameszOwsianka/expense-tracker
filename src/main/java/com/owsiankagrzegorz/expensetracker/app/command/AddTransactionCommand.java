package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;


public class AddTransactionCommand implements Command {

    private final AppContext ctx;
    private static final Logger log = LoggerFactory.getLogger(AddTransactionCommand.class);

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

        log.info("Added transaction: id={}, type={}, category={}, amount={}",
                transaction.getId(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getAmount());

        ctx.printer().info("Transaction added with ID: " + id);
    }
}