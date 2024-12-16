package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;
import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    property = {
        CommandProcessor.COMMAND_SCOPE + "=expensetracker",
        CommandProcessor.COMMAND_FUNCTION + "=addExpense",
        CommandProcessor.COMMAND_FUNCTION + "=editExpense"  // Adding the editExpense command
    },
    service = ExpenseTrackerCommands.class
)
public class ExpenseTrackerCommands {

    private ExpenseService expenseService;

    @Reference
    public void setExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void addExpense(String name, double amount) {
        Expense expense = new Expense(name, amount);
        expenseService.addExpense(expense);
        System.out.println("Expense added: " + expense.getName());
    }
    
 // Command to edit an expense
    public void editExpense(String id, String name, double amount) {
        Expense updatedExpense = new Expense(name, amount);
        expenseService.editExpense(id, updatedExpense);
        System.out.println("Expense with ID " + id + " updated to: " + updatedExpense.getName());
    }
}