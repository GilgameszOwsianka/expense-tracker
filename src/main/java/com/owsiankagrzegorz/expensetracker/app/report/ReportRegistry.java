package com.owsiankagrzegorz.expensetracker.app.report;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportRegistry {

    private final Map<String, ReportStrategy> strategies = new LinkedHashMap<>();

    public void register(ReportStrategy strategy) {
        strategies.put(strategy.key(), strategy);
    }

    public ReportStrategy get(String key) {
        return strategies.get(key);
    }

    public Map<String, ReportStrategy> all() {
        return strategies;
    }
}