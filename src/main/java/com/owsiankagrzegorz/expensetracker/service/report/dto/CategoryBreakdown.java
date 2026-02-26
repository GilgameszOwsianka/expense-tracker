package com.owsiankagrzegorz.expensetracker.service.report.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class CategoryBreakdown {

    private final Map<String, Double> totalsByCategory;

    public CategoryBreakdown(Map<String, Double> totalsByCategory) {
        Objects.requireNonNull(totalsByCategory, "totalsByCategory");
        this.totalsByCategory = Collections.unmodifiableMap(new LinkedHashMap<>(totalsByCategory));
    }

    public Map<String, Double> getTotalsByCategory() {
        return totalsByCategory;
    }
}