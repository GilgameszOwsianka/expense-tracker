package com.owsiankagrzegorz.expensetracker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Transaction {

    private long id;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private TransactionType type;

    public Transaction(long id, BigDecimal amount, String category, LocalDate date, TransactionType type) {
        this.id = id;
        this.amount = normalizeAmount(amount);
        this.category = category;
        this.date = date;
        this.type = type;
    }

    public void setId(long id) {this.id = id;}

    public void setAmount(BigDecimal amount) {
        this.amount = normalizeAmount(amount);
    }

    public void setCategory(String category) {this.category = category;}

    public void setDate(LocalDate date) {this.date = date;}

    public void setType(TransactionType type) {this.type = type;}

    private BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}