package com.owsiankagrzegorz.expensetracker.service.report.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public final class CategoryBreakdown {
    private final Map<String, BigDecimal> totalsByCategory;

    public CategoryBreakdown(Map<String, BigDecimal> totalsByCategory) {
        Objects.requireNonNull(totalsByCategory, "totalsByCategory");
        this.totalsByCategory = Collections.unmodifiableMap(new LinkedHashMap<>(totalsByCategory));
    }
}