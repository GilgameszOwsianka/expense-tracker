package com.owsiankagrzegorz.expensetracker.service.report.dto;

import java.time.YearMonth;
import java.util.Objects;

public final class MonthlySummary {

    private final YearMonth month;
    private final double incomeTotal;
    private final double expenseTotal;
    private final double balance;

    public MonthlySummary(YearMonth month, double incomeTotal, double expenseTotal) {
        this.month = Objects.requireNonNull(month, "month");
        this.incomeTotal = incomeTotal;
        this.expenseTotal = expenseTotal;
        this.balance = incomeTotal - expenseTotal;
    }

    public YearMonth getMonth() {
        return month;
    }

    public double getIncomeTotal() {
        return incomeTotal;
    }

    public double getExpenseTotal() {
        return expenseTotal;
    }

    public double getBalance() {
        return balance;
    }
}
