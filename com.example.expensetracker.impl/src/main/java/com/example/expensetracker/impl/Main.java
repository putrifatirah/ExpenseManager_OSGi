package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the ExpenseServiceImpl
        ExpenseService service = new ExpenseServiceImpl();

        // Test adding an expense
        Expense expense1 = new Expense("123", "Food", 50.75, "2024-12-15");
        service.addExpense(expense1);
        System.out.println("Added expense: " + expense1.getName());

        // Test viewing expenses
        System.out.println("Viewing all expenses:");
        service.viewExpenses().forEach(expense -> 
            System.out.println("ID: " + expense.getId() + ", Name: " + expense.getName() + 
                               ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate())
        );

        // Test editing an expense
        Expense updatedExpense = new Expense("123", "Food", 60.50, "2024-12-16");
        service.editExpense("123", updatedExpense);
        System.out.println("Updated expense: " + updatedExpense.getName());

        // Test calculating the total amount
        double total = service.calculateTotal();
        System.out.println("Total expenses: " + total);

        // Test deleting an expense
        service.deleteExpense("123");
        System.out.println("Deleted expense with ID: 123");

        // Verify the remaining expenses
        System.out.println("Remaining expenses:");
        service.viewExpenses().forEach(expense -> 
            System.out.println("ID: " + expense.getId() + ", Name: " + expense.getName() + 
                               ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate())
        );
    }
}
