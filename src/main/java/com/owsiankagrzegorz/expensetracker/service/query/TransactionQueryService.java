package com.owsiankagrzegorz.expensetracker.service.query;

import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionQueryService {

    private final TransactionRepository repository;

    public TransactionQueryService(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public List<Transaction> find(TransactionQuery query) {
        Objects.requireNonNull(query, "query");

        Stream<Transaction> stream = repository.findAll().stream();

        if (query.getType().isPresent()) {
            var type = query.getType().get();
            stream = stream.filter(t -> t.getType() == type);
        }

        if (query.getDateFrom().isPresent()) {
            var from = query.getDateFrom().get();
            stream = stream.filter(t -> !t.getDate().isBefore(from));
        }

        if (query.getDateTo().isPresent()) {
            var to = query.getDateTo().get();
            stream = stream.filter(t -> !t.getDate().isAfter(to));
        }

        if (query.getCategory().isPresent()) {
            var category = query.getCategory().get();
            stream = stream.filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(category));
        }

        if (query.getMinAmount().isPresent()) {
            var min = query.getMinAmount().get();
            stream = stream.filter(t -> t.getAmount() >= min);
        }

        if (query.getMaxAmount().isPresent()) {
            var max = query.getMaxAmount().get();
            stream = stream.filter(t -> t.getAmount() <= max);
        }

        if (query.getSortSpec().isPresent()) {
            stream = stream.sorted(toComparator(query.getSortSpec().get()));
        }

        if (query.getLimit().isPresent()) {
            stream = stream.limit(query.getLimit().get());
        }

        return stream.toList();
    }

    private Comparator<Transaction> toComparator(SortSpec spec) {
        Comparator<Transaction> comparator = switch (spec.getField()) {
            case DATE -> Comparator.comparing(Transaction::getDate);
            case AMOUNT -> Comparator.comparingDouble(Transaction::getAmount);
            case CATEGORY -> Comparator.comparing(t -> t.getCategory() == null ? "" : t.getCategory(), String.CASE_INSENSITIVE_ORDER);
            case TYPE -> Comparator.comparing(t -> t.getType().name());
        };

        if (spec.getDirection() == SortDirection.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}
