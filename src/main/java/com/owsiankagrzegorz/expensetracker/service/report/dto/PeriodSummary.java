package com.owsiankagrzegorz.expensetracker.service.report.dto;

import java.math.BigDecimal;
import java.util.Objects;

public final class PeriodSummary {
    private final BigDecimal incomeTotal;
    private final BigDecimal expenseTotal;
    private final BigDecimal balance;

    public PeriodSummary(BigDecimal incomeTotal, BigDecimal expenseTotal) {
        this.incomeTotal = Objects.requireNonNull(incomeTotal, "incomeTotal");
        this.expenseTotal = Objects.requireNonNull(expenseTotal, "expenseTotal");
        this.balance = this.incomeTotal.subtract(this.expenseTotal);
    }

    public BigDecimal getIncomeTotal() { return incomeTotal; }
    public BigDecimal getExpenseTotal() { return expenseTotal; }
    public BigDecimal getBalance() { return balance; }
}