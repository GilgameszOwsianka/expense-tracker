package com.owsiankagrzegorz.expensetracker.model;

import java.time.LocalDate;

public class Transaction {
    private double amount;
    private String category;
    private LocalDate date;
    private TransactionType type;
    private long id;

    //Konstruktor
    public Transaction (long id, double amount, String category, LocalDate date, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
    }

    //Gettery i settery
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    //toString
    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                '}';
    }
}
