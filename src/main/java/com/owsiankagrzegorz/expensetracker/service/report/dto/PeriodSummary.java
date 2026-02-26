package com.owsiankagrzegorz.expensetracker.service.report.dto;

public final class PeriodSummary {

    private final double incomeTotal;
    private final double expenseTotal;
    private final double balance;

    public PeriodSummary(double incomeTotal, double expenseTotal) {
        this.incomeTotal = incomeTotal;
        this.expenseTotal = expenseTotal;
        this.balance = incomeTotal - expenseTotal;
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
