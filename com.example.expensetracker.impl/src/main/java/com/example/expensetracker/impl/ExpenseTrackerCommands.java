package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;
import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component(
    property = {
        CommandProcessor.COMMAND_SCOPE + "=expensetracker",
        CommandProcessor.COMMAND_FUNCTION + "=addExpense",
        CommandProcessor.COMMAND_FUNCTION + "=editExpense",
        CommandProcessor.COMMAND_FUNCTION + "=viewExpenses",
        CommandProcessor.COMMAND_FUNCTION + "=deleteExpense",
        CommandProcessor.COMMAND_FUNCTION + "=analyzeExpenses"
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
        if (name == null || name.isBlank() || amount <= 0) {
            System.out.println("Usage: addExpense [name: String] [amount: double]");
            System.out.println("Example: addExpense \"Lunch\" 12.50");
            return;
        }
        expenseService.addExpense(new Expense(name, amount));
        System.out.println("Expense added: " + name);
    }

    public void editExpense(String id, String name, double amount) {
        if (id == null || id.isBlank() || name == null || name.isBlank() || amount <= 0) {
            System.out.println("Usage: editExpense [id: String] [name: String] [amount: double]");
            System.out.println("Example: editExpense \"123abc\" \"Dinner\" 15.75");
            return;
        }
        expenseService.editExpense(id, new Expense(name, amount));
        System.out.println("Expense updated: " + name);
    }

    public void viewExpenses() {
        System.out.println("\n--- All Expenses ---");
        expenseService.viewExpenses().forEach(expense ->
            System.out.println("ID: " + expense.getId() + ", Name: " + expense.getName() +
                               ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate()));
        System.out.printf("\n                    ------------------------------");
        System.out.printf("\n                        Grand Total: RM %.2f   ", grandTotal());
        System.out.printf("\n                    ------------------------------\n");
    }

    public void deleteExpense(String id) {
        if (id == null || id.isBlank()) {
            System.out.println("Usage: deleteExpense [id: String]");
            System.out.println("Example: deleteExpense \"123abc\"");
            return;
        }
        expenseService.deleteExpense(id);
        System.out.println("Deleted expense with ID: " + id);
    }
    
    public double grandTotal() {
        double total = expenseService.calculateTotal();
        return total;
    }
    
    public void analyzeExpenses() {
        List<LocalDate> distinctDates = expenseService.getDistinctDates();

        if (distinctDates.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        LocalDate maxDate = distinctDates.stream()
                .max(Comparator.comparing(date -> expenseService.getExpensesByDate(date).stream().mapToDouble(Expense::getAmount).sum()))
                .orElse(null);

        LocalDate minDate = distinctDates.stream()
                .min(Comparator.comparing(date -> expenseService.getExpensesByDate(date).stream().mapToDouble(Expense::getAmount).sum()))
                .orElse(null);

        if (maxDate != null) {
            double maxTotal = expenseService.getExpensesByDate(maxDate).stream().mapToDouble(Expense::getAmount).sum();
            System.out.printf("\n--- Date with Most Expenses ---\n");
            System.out.printf("Date: %s, Total Amount: %.2f\n", maxDate, maxTotal);
        }

        if (minDate != null) {
            double minTotal = expenseService.getExpensesByDate(minDate).stream().mapToDouble(Expense::getAmount).sum();
            System.out.printf("\n--- Date with Least Expenses ---\n");
            System.out.printf("Date: %s, Total Amount: %.2f\n", minDate, minTotal);
        }
    }
}