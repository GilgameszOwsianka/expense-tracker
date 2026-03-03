package com.owsiankagrzegorz.expensetracker.service.report;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.report.dto.CategoryBreakdown;
import com.owsiankagrzegorz.expensetracker.service.report.dto.MonthlySummary;
import com.owsiankagrzegorz.expensetracker.service.report.dto.PeriodSummary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionReportService {
    private final TransactionRepository repository;

    public TransactionReportService(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public PeriodSummary reportPeriod(LocalDate from, LocalDate to) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");

        var filtered = repository.findAll().stream()
                .filter(t -> !t.getDate().isBefore(from))
                .filter(t -> !t.getDate().isAfter(to))
                .toList();

        BigDecimal income = filtered.stream()
                .filter(t -> t.getType() == TransactionType.PRZYCHOD)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = filtered.stream()
                .filter(t -> t.getType() == TransactionType.WYDATEK)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PeriodSummary(income, expense);
    }

    public MonthlySummary reportMonthly(YearMonth month) {
        Objects.requireNonNull(month, "month");

        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();

        PeriodSummary period = reportPeriod(from, to);
        return new MonthlySummary(month, period.getIncomeTotal(), period.getExpenseTotal());
    }

    public CategoryBreakdown reportByCategory(LocalDate from, LocalDate to, TransactionType typeOrNull) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");

        var stream = repository.findAll().stream()
                .filter(t -> !t.getDate().isBefore(from))
                .filter(t -> !t.getDate().isAfter(to));

        if (typeOrNull != null) {
            stream = stream.filter(t -> t.getType() == typeOrNull);
        }

        Map<String, BigDecimal> totals = stream.collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
        ));

        Map<String, BigDecimal> sorted = totals.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        return new CategoryBreakdown(sorted);
    }
}