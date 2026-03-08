package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class PeriodSummaryReportStrategy implements ReportStrategy {

    private static final Logger log = LoggerFactory.getLogger(PeriodSummaryReportStrategy.class);

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
        LocalDate from = ctx.input().readOptionalDate("From date (yyyy-MM-dd): ");
        LocalDate to = ctx.input().readOptionalDate("To date (yyyy-MM-dd): ");

        if (from == null || to == null) {
            log.warn("Period summary report aborted: missing date range, from={}, to={}", from, to);
            ctx.printer().error("Both dates are required.");
            return;
        }

        if (from.isAfter(to)) {
            log.warn("Period summary report aborted: invalid date range, from={}, to={}", from, to);
            ctx.printer().error("'From' date cannot be after 'To' date.");
            return;
        }

        log.info("Running period summary report: from={}, to={}", from, to);

        var summary = ctx.reportService().reportPeriod(from, to);

        ctx.printer().separator();
        ctx.printer().info("Period:  " + from + " to " + to);
        ctx.printer().separator();
        ctx.printer().info("Income:  " + ctx.printer().formatAmount(summary.getIncomeTotal()));
        ctx.printer().info("Expense: " + ctx.printer().formatAmount(summary.getExpenseTotal()));
        ctx.printer().separator();
        ctx.printer().info("Balance: " + ctx.printer().formatAmount(summary.getBalance()));
        ctx.printer().separator();

        log.info("Period summary report generated: from={}, to={}", from, to);
    }
}