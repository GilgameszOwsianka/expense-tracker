package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class CategoryBreakdownReportStrategy implements ReportStrategy {

    private static final Logger log = LoggerFactory.getLogger(CategoryBreakdownReportStrategy.class);

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
            log.warn("Category breakdown report aborted: missing 'from' date");
            ctx.printer().error("From date is required.");
            return;
        }

        LocalDate to = ctx.input().readOptionalDate("To (yyyy-MM-dd): ");
        if (to == null) {
            log.warn("Category breakdown report aborted: missing 'to' date, from={}", from);
            ctx.printer().error("To date is required.");
            return;
        }

        if (from.isAfter(to)) {
            log.warn("Category breakdown report aborted: invalid date range, from={}, to={}", from, to);
            ctx.printer().error("Invalid range: From date must be <= To date.");
            return;
        }

        TransactionType type = ctx.input()
                .readOptionalTransactionType("Type (WYDATEK/PRZYCHOD or empty for all): ");

        log.info("Running category breakdown report: from={}, to={}, type={}", from, to, type);

        var breakdown = ctx.reportService().reportByCategory(from, to, type);

        if (breakdown.getTotalsByCategory().isEmpty()) {
            log.info("Category breakdown report returned no data: from={}, to={}, type={}", from, to, type);
            ctx.printer().info("No data for selected criteria.");
            return;
        }

        int maxCategoryWidth = breakdown.getTotalsByCategory().keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(10);

        ctx.printer().separator();
        ctx.printer().info("Category breakdown");
        ctx.printer().separator();

        breakdown.getTotalsByCategory().forEach((category, total) -> {
            String line = String.format(
                    "%-" + maxCategoryWidth + "s : %s",
                    category,
                    ctx.printer().formatAmount(total)
            );
            ctx.printer().info(line);
        });

        ctx.printer().separator();

        log.info("Category breakdown report generated: from={}, to={}, type={}, categories={}",
                from, to, type, breakdown.getTotalsByCategory().size());
    }
}