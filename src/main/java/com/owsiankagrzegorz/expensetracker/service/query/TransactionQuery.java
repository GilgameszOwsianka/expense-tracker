package com.owsiankagrzegorz.expensetracker.service.query;

import com.owsiankagrzegorz.expensetracker.model.TransactionType;

import java.time.LocalDate;
import java.util.Optional;

public final class TransactionQuery {

    private final TransactionType type;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String category;
    private final Double minAmount;
    private final Double maxAmount;
    private final SortSpec sortSpec;
    private final Integer limit;

    private TransactionQuery(Builder builder) {
        this.type = builder.type;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
        this.category = builder.category;
        this.minAmount = builder.minAmount;
        this.maxAmount = builder.maxAmount;
        this.sortSpec = builder.sortSpec;
        this.limit = builder.limit;
    }

    public Optional<TransactionType> getType() {
        return Optional.ofNullable(type);
    }

    public Optional<LocalDate> getDateFrom() {
        return Optional.ofNullable(dateFrom);
    }

    public Optional<LocalDate> getDateTo() {
        return Optional.ofNullable(dateTo);
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }

    public Optional<Double> getMinAmount() {
        return Optional.ofNullable(minAmount);
    }

    public Optional<Double> getMaxAmount() {
        return Optional.ofNullable(maxAmount);
    }

    public Optional<SortSpec> getSortSpec() {
        return Optional.ofNullable(sortSpec);
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static TransactionQuery empty() {
        return builder().build();
    }

    public static final class Builder {
        private TransactionType type;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String category;
        private Double minAmount;
        private Double maxAmount;
        private SortSpec sortSpec;
        private Integer limit;

        private Builder() {}

        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder dateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder dateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public Builder category(String category) {
            this.category = (category == null || category.isBlank()) ? null : category.trim();
            return this;
        }

        public Builder minAmount(Double minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public Builder maxAmount(Double maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public Builder sort(SortSpec sortSpec) {
            this.sortSpec = sortSpec;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public TransactionQuery build() {
            // lightweight validation (no heavy logic yet)
            if (limit != null && limit <= 0) {
                throw new IllegalArgumentException("limit must be > 0");
            }
            if (minAmount != null && minAmount < 0) {
                throw new IllegalArgumentException("minAmount must be >= 0");
            }
            if (maxAmount != null && maxAmount < 0) {
                throw new IllegalArgumentException("maxAmount must be >= 0");
            }
            if (minAmount != null && maxAmount != null && minAmount > maxAmount) {
                throw new IllegalArgumentException("minAmount must be <= maxAmount");
            }
            if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
                throw new IllegalArgumentException("dateFrom must be <= dateTo");
            }
            return new TransactionQuery(this);
        }
    }
}
