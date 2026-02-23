package com.owsiankagrzegorz.expensetracker.model;

import java.util.Locale;

public enum TransactionType {
    WYDATEK,
    PRZYCHOD;

    public static TransactionType fromString (String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }

        String v = raw.trim().toUpperCase(Locale.ROOT);

        v = v.replace("ó", "o");

        if (v.equals("EXPENSE")) return WYDATEK;
        if (v.equals("INCOME")) return PRZYCHOD;

        return TransactionType.valueOf(v);
    }
}