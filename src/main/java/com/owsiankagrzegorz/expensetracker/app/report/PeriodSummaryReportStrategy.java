package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;

import java.time.LocalDate;

public class PeriodSummaryReportStrategy implements ReportStrategy {

    @Override
    public String key() {
        return "2";
    }

    @Override
    public String label() {
        return "Period summary";
    }

    @Override
    public void execute(AppContext ctx) {
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

        var summary = ctx.reportService().reportPeriod(from, to);

        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();
    }
}