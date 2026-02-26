package com.owsiankagrzegorz.expensetracker.service.report;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.report.dto.CategoryBreakdown;
import com.owsiankagrzegorz.expensetracker.service.report.dto.MonthlySummary;
import com.owsiankagrzegorz.expensetracker.service.report.dto.PeriodSummary;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionReportService {

    private final TransactionRepository repository;

    public TransactionReportService(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public PeriodSummary reportPeriod(LocalDate from, LocalDate to) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");

        Stream<Transaction> stream = repository.findAll().stream()
                .filter(t -> !t.getDate().isBefore(from))
                .filter(t -> !t.getDate().isAfter(to));

        double income = stream
                .filter(t -> t.getType() == TransactionType.PRZYCHOD)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = repository.findAll().stream()
                .filter(t -> !t.getDate().isBefore(from))
                .filter(t -> !t.getDate().isAfter(to))
                .filter(t -> t.getType() == TransactionType.WYDATEK)
                .mapToDouble(Transaction::getAmount)
                .sum();

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

        Stream<Transaction> stream = repository.findAll().stream()
                .filter(t -> !t.getDate().isBefore(from))
                .filter(t -> !t.getDate().isAfter(to));

        if (typeOrNull != null) {
            stream = stream.filter(t -> t.getType() == typeOrNull);
        }

        Map<String, Double> totals = stream.collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.summingDouble(Transaction::getAmount)
        ));

        Map<String, Double> sorted = totals.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        return new CategoryBreakdown(sorted);
    }
}
