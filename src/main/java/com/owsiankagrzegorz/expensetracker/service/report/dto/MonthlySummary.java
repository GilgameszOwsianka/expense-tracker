package com.owsiankagrzegorz.expensetracker.service.report.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
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
}