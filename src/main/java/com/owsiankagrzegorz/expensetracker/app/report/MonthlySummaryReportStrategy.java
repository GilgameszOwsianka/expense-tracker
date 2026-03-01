package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;

import java.time.YearMonth;

public class MonthlySummaryReportStrategy implements ReportStrategy {

    @Override
    public String key() {
        return "1";
    }

    @Override
    public String label() {
        return "Monthly summary";
    }

    @Override
    public void execute(AppContext ctx) {
        YearMonth month = ctx.input().readOptionalYearMonth("Enter month (yyyy-MM): ");
        if (month == null) {
            ctx.printer().error("Month is required");
            return;
        }

        var summary = ctx.reportService().reportMonthly(month);

        ctx.printer().separator();
        ctx.printer().info("Month:   " + summary.getMonth());
        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();
    }
}