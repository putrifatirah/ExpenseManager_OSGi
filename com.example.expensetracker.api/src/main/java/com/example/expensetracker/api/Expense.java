package com.example.expensetracker.api;

public class Expense {
    private String id;
    private String name;
    private double amount;
    private String date;

    // Constructors, Getters, and Setters
    public Expense(String id, String name, double amount, String date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
