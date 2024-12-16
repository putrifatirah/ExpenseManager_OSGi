package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;

import java.time.LocalDate;

import org.apache.felix.service.command.Descriptor;

public class Main {
    private final ExpenseService service;

    // Constructor to initialize the ExpenseService
    public Main(ExpenseService service) {
        this.service = service;
    }

    @Descriptor("Add an expense: [name] [amount] [date] (Date format: YYYY-MM-DD)")
    public void addExpense(String name, double amount) {

            Expense expense = new Expense(name, amount);  // No need to pass an ID; MongoDB generates it
            service.addExpense(expense);
            System.out.println("Added expense: " + expense.getName());

    }

    @Descriptor("View all expenses")
    public void viewExpenses() {
        System.out.println("Viewing all expenses:");
        service.viewExpenses().forEach(expense -> 
            System.out.println("ID: " + expense.getId() + ", Name: " + expense.getName() + 
                               ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate())
        );
    }

    @Descriptor("Edit an expense: [id] [name] [amount] [date]")
    public void editExpense(String id, String name, double amount) {


            Expense updatedExpense = new Expense(name, amount);  // Don't pass an ID here; it's part of the update logic
            service.editExpense(id, updatedExpense);
            System.out.println("Updated expense: " + updatedExpense.getName());

    }

    @Descriptor("Calculate the total of all expenses")
    public void calculateTotal() {
        double total = service.calculateTotal();
        System.out.println("Total expenses: " + total);
    }

    @Descriptor("Delete an expense: [id]")
    public void deleteExpense(String id) {
        service.deleteExpense(id);
        System.out.println("Deleted expense with ID: " + id);
    }

}