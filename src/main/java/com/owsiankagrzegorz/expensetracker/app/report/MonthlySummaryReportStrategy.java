package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;

public class MonthlySummaryReportStrategy implements ReportStrategy {

    private static final Logger log = LoggerFactory.getLogger(MonthlySummaryReportStrategy.class);

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
            log.warn("Monthly summary report aborted: month not provided");
            ctx.printer().error("Month is required");
            return;
        }

        log.info("Running monthly summary report: month={}", month);

        var summary = ctx.reportService().reportMonthly(month);

        ctx.printer().separator();
        ctx.printer().info("Month:   " + summary.getMonth());
        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();

        log.info("Monthly summary report generated: month={}", month);
    }
}