package com.example.expensetracker.api;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    void addExpense(Expense expense);
    void editExpense(String id, Expense updatedExpense);
    void deleteExpense(String id);
    List<Expense> viewExpenses();
    double calculateTotal();
	List<LocalDate> getDistinctDates();
	List<Expense> getExpensesByDate(LocalDate date);
}
