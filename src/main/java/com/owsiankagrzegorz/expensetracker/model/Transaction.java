package com.owsiankagrzegorz.expensetracker.model;

import java.time.LocalDate;

public class Transaction {
    private double amount;
    private String category;
    private LocalDate date;
    private String type; // "WYDATEK" lub "Przychód"

    //Konstruktor
    public Transaction (double amount, String category, LocalDate date, String type) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
    }

    //Gettery i settery
    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

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
