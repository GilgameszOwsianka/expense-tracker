package com.owsiankagrzegorz.expensetracker.service.report.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

public final class MonthlySummary {
    private final YearMonth month;
    private final BigDecimal incomeTotal;
    private final BigDecimal expenseTotal;
    private final BigDecimal balance;

    public MonthlySummary(YearMonth month, BigDecimal incomeTotal, BigDecimal expenseTotal) {
        this.month = Objects.requireNonNull(month, "month");
        this.incomeTotal = Objects.requireNonNull(incomeTotal, "incomeTotal");
        this.expenseTotal = Objects.requireNonNull(expenseTotal, "expenseTotal");
        this.balance = this.incomeTotal.subtract(this.expenseTotal);
    }

    public YearMonth getMonth() { return month; }
    public BigDecimal getIncomeTotal() { return incomeTotal; }
    public BigDecimal getExpenseTotal() { return expenseTotal; }
    public BigDecimal getBalance() { return balance; }
}