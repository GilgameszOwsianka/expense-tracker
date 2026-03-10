package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalsSummaryReportStrategy implements ReportStrategy {

    private static final Logger log = LoggerFactory.getLogger(TotalsSummaryReportStrategy.class);

    @Override
    public String key() {
        return "4";
    }

    @Override
    public String label() {
        return "Totals summary";
    }

    @Override
    public void execute(AppContext ctx) {
        log.info("Running totals summary report");

        var summary = ctx.reportService().reportTotals();

        ctx.printer().separator();
        ctx.printer().info("Totals summary");
        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();

        log.info("Totals summary report generated");
    }
}