package com.owsiankagrzegorz.expensetracker.service.query;

import java.util.Objects;

public final class SortSpec {
    private final SortField field;
    private final SortDirection direction;

    public SortSpec(SortField field, SortDirection direction) {
        this.field = Objects.requireNonNull(field, "field");
        this.direction = Objects.requireNonNull(direction, "direction");
    }

    public SortField getField() {
        return field;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public static SortSpec of(SortField field, SortDirection direction) {
        return new SortSpec(field, direction);
    }

    public static SortSpec byDateAsc() {
        return new SortSpec(SortField.DATE, SortDirection.ASC);
    }

    public static SortSpec byDateDesc() {
        return new SortSpec(SortField.DATE, SortDirection.DESC);
    }

    public static SortSpec byAmountAsc() {
        return new SortSpec(SortField.AMOUNT, SortDirection.ASC);
    }

    public static SortSpec byAmountDesc() {
        return new SortSpec(SortField.AMOUNT, SortDirection.DESC);
    }
}
