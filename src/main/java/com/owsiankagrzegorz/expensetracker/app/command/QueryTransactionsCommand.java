package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.service.query.SortDirection;
import com.owsiankagrzegorz.expensetracker.service.query.SortField;
import com.owsiankagrzegorz.expensetracker.service.query.SortSpec;
import com.owsiankagrzegorz.expensetracker.service.query.TransactionQuery;

import java.math.BigDecimal;
import java.time.LocalDate;

public class QueryTransactionsCommand implements Command {

    private final AppContext ctx;

    public QueryTransactionsCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().info("\n--- Query / Filter ---");

        TransactionQuery.Builder builder = TransactionQuery.builder();

        // 1) Type (optional)
        TransactionType type = ctx.input().readOptionalTransactionType("Filter by type (WYDATEK/PRZYCHOD or empty): ");
        if (type != null) builder.type(type);

        // 2) Date range (optional, validated)
        LocalDate dateFrom;
        LocalDate dateTo;
        while (true) {
            dateFrom = ctx.input().readOptionalDate("Date from (yyyy-MM-dd or empty): ");
            dateTo = ctx.input().readOptionalDate("Date to (yyyy-MM-dd or empty): ");

            if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
                ctx.printer().error("Invalid range: dateFrom must be <= dateTo. Try again.");
                continue;
            }
            break;
        }
        if (dateFrom != null) builder.dateFrom(dateFrom);
        if (dateTo != null) builder.dateTo(dateTo);

        // 3) Category (optional)
        ctx.printer().info("Category (or empty): ");
        String category = ctx.input().readLineTrimmed();
        if (!category.isEmpty()) builder.category(category);

        // 4) Amount range (optional)
        Double[] range = ctx.input().readOptionalAmountRange();

        if (range[0] != null) {
            builder.minAmount(BigDecimal.valueOf(range[0]));
        }
        if (range[1] != null) {
            builder.maxAmount(BigDecimal.valueOf(range[1]));
        }

        // 5) Sorting (optional)
        SortField sortField = ctx.input().readOptionalSortField("Sort field (DATE/AMOUNT/CATEGORY/TYPE or empty): ");
        if (sortField != null) {
            SortDirection direction = ctx.input().readOptionalSortDirection("Sort direction (ASC/DESC or empty=ASC): ");
            if (direction == null) direction = SortDirection.ASC;
            builder.sort(SortSpec.of(sortField, direction));
        }

        // 6) Limit (optional)
        Integer limit = ctx.input().readOptionalInt("Limit results (or empty): ");
        if (limit != null) builder.limit(limit);

        // 7) Build query
        TransactionQuery query;
        try {
            query = builder.build();
        } catch (IllegalArgumentException e) {
            ctx.printer().error("Invalid query: " + e.getMessage());
            return;
        }

        var results = ctx.queryService().find(query);

        if (results.isEmpty()) {
            ctx.printer().info("No transactions found.");
            return;
        }

        ctx.printer().info("\nResults (" + results.size() + "):");
        results.forEach(t -> ctx.printer().info(t.toString()));
    }
}