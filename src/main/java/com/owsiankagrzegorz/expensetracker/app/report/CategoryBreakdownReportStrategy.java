package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;

import java.time.LocalDate;

public class CategoryBreakdownReportStrategy implements ReportStrategy {

    @Override
    public String key() {
        return "3";
    }

    @Override
    public String label() {
        return "Category breakdown (optional type)";
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