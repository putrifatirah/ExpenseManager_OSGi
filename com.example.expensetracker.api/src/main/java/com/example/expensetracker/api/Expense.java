package com.example.expensetracker.api;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Converts LocalDate to java.util.Date (for MongoDB storage)
 // Converts LocalDateTime to java.util.Date (for MongoDB storage)
    public Date getMongoDate() {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());  // Convert LocalDateTime to Date
    }
}