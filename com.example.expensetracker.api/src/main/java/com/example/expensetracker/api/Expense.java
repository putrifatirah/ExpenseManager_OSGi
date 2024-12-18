package com.example.expensetracker.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.bson.types.ObjectId;

public class Expense {
    private ObjectId id;  // MongoDB's default _id field
    private String name;
    private double amount;
    private LocalDateTime date;

    // Constructor that sets the date automatically to the current date
    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.date = LocalDateTime.now();  // Sets the date to the current date
    }

    // Constructor to include ObjectId and date explicitly
    public Expense(ObjectId id, String name, double amount, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Updated getDate to return only the date part
    public LocalDate getDate() {
        return date.toLocalDate();  // Returns only the date part
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Converts LocalDateTime to java.util.Date (for MongoDB storage)
    public Date getMongoDate() {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());  // Convert LocalDateTime to Date
    }
    
    @Override
    public String toString() {
        return String.format("Expense{id=%s, name='%s', amount=%.2f, date=%s}", 
                id, name, amount, getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return Double.compare(expense.amount, amount) == 0 &&
               name.equals(expense.name) &&
               date.toLocalDate().equals(expense.date.toLocalDate());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
}
