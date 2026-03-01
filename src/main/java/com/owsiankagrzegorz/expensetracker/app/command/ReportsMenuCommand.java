package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.model.TransactionType;

import java.time.LocalDate;
import java.time.YearMonth;

public class ReportsMenuCommand implements Command {

    private final AppContext ctx;

    public ReportsMenuCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {

        ctx.printer().info("\n--- Reports ---");
        ctx.printer().info("1) Monthly summary");
        ctx.printer().info("2) Period summary");
        ctx.printer().info("3) Category breakdown (optional type)");

        ctx.printer().info("Choose option: ");
        String option = ctx.input().readLineTrimmed();

        switch (option) {

            case "1" -> monthly();

            case "2" -> period();

            case "3" -> categoryBreakdown();

            default -> ctx.printer().error("Unknown option.");
        }
    }

    private void monthly() {
        YearMonth month = ctx.input().readOptionalYearMonth("Enter month (yyyy-MM): ");
        if (month == null) {
            ctx.printer().error("Month is required");
            return;
        }

        var summary = ctx.reportService().reportMonthly(month);

        ctx.printer().separator();
        ctx.printer().info("Month: " + summary.getMonth());
        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();
    }

    private void period() {
        LocalDate from = ctx.input().readOptionalDate("From (yyyy-MM-dd): ");
        if (from == null) {
            ctx.printer().error("From date is required.");
            return;
        }

        LocalDate to = ctx.input().readOptionalDate("To (yyyy-MM-dd): ");
        if (to == null) {
            ctx.printer().error("To date is required.");
            return;
        }

        var summary = ctx.reportService().reportPeriod(from, to);

        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();
    }

    private void categoryBreakdown() {

        LocalDate from = ctx.input().readOptionalDate("From (yyyy-MM-dd): ");
        if (from == null) {
            ctx.printer().error("From date is required.");
            return;
        }

        LocalDate to = ctx.input().readOptionalDate("To (yyyy-MM-dd): ");
        if (to == null) {
            ctx.printer().error("To date is required.");
            return;
        }

        if (from.isAfter(to)) {
            ctx.printer().error("Invalid range: From date must be <= To date.");
            return;
        }

        TransactionType type = ctx.input()
                .readOptionalTransactionType("Type (WYDATEK/PRZYCHOD or empty for all): ");

        var breakdown = ctx.reportService().reportByCategory(from, to, type);

        if (breakdown.getTotalsByCategory().isEmpty()) {
            ctx.printer().info("No data for selected criteria.");
            return;
        }

        ctx.printer().separator();
        breakdown.getTotalsByCategory()
                .forEach((category, total) ->
                        ctx.printer().info(category + " : " + ctx.printer().formatAmount(total))
                );
        ctx.printer().separator();
    }
}