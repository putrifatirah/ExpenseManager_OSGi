package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;

import java.time.LocalDate;
import java.util.Scanner;

import org.apache.felix.service.command.Descriptor;

/*public class Main {
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

   /* @Descriptor("Delete an expense: [id]")
    public void deleteExpense(String id) {
        service.deleteExpense(id);
        System.out.println("Deleted expense with ID: " + id);
    }*/
  /*  @Descriptor("Delete an expense: Usage: deleteExpense [id: String]")
    public void deleteExpense(String id) {
        if (id == null || id.isBlank()) {
            System.out.println("Usage: deleteExpense [id: String]");
            System.out.println("Example: deleteExpense \"60f5c2d1f3a4e3b2d4c5e6a1\"");
            return;
        }
        service.deleteExpense(id);
        System.out.println("Deleted expense with ID: " + id);
    }
} 
*/
/*public class Main {
    private final ExpenseService service;

    public Main(ExpenseService service) {
        this.service = service;
    }

    @Descriptor("Add an expense: Usage -> addExpense [name] [amount]")
    public void addExpense(String name, double amount) {
        if (name == null || name.isBlank() || amount <= 0) {
            System.out.println("\nUsage: addExpense [name: String] [amount: double]");
            System.out.println("Example: addExpense \"Lunch\" 12.50\n");
            return;
        }
        Expense expense = new Expense(name, amount);
        service.addExpense(expense);
        System.out.println("Expense added: " + expense.getName());
    }

    @Descriptor("Edit an expense: Usage -> editExpense [id] [name] [amount]")
    public void editExpense(String id, String name, double amount) {
        if (id == null || id.isBlank() || name == null || name.isBlank() || amount <= 0) {
            System.out.println("\nUsage: editExpense [id: String] [name: String] [amount: double]");
            System.out.println("Example: editExpense \"60f5c2d1f3a4e3b2d4c5e6a1\" \"Dinner\" 15.75\n");
            return;
        }
        Expense updatedExpense = new Expense(name, amount);
        service.editExpense(id, updatedExpense);
        System.out.println("Expense with ID " + id + " updated to: " + updatedExpense.getName());
    }

    @Descriptor("View all expenses")
    public void viewExpenses() {
        System.out.println("\n--- Viewing All Expenses ---");
        var expenses = service.viewExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.\n");
            return;
        }
        expenses.forEach(expense ->
            System.out.println("ID: " + expense.getId() + ", Name: " + expense.getName() +
                               ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate())
        );
    }

    @Descriptor("Delete an expense: Usage -> deleteExpense [id]")
    public void deleteExpense(String id) {
        if (id == null || id.isBlank()) {
            System.out.println("\nUsage: deleteExpense [id: String]");
            System.out.println("Example: deleteExpense \"60f5c2d1f3a4e3b2d4c5e6a1\"\n");
            return;
        }
        service.deleteExpense(id);
        System.out.println("Expense with ID " + id + " has been deleted.");
    }

    @Descriptor("Calculate the total of all expenses")
    public void calculateTotal() {
        double total = service.calculateTotal();
        System.out.println("\nTotal expenses: " + total + "\n");
    }
}
*/
public class Main {
    private final ExpenseService service;

    public Main(ExpenseService service) {
        this.service = service;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nExpense Manager");
            System.out.println("1. View Expenses");
            System.out.println("2. Add Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. Edit Expense");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewExpenses();
                case 2 -> addExpense(scanner);
                case 3 -> deleteExpense(scanner);
                case 4 -> editExpense(scanner);
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewExpenses() {
        System.out.println("\n--- Viewing All Expenses ---");
        var expenses = service.viewExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            expenses.forEach(expense ->
                System.out.println(", Name: " + expense.getName() +
                        ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate())
            );
        }
    }

    private void addExpense(Scanner scanner) {
        System.out.print("Enter expense name: ");
        String name = scanner.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Expense expense = new Expense(name, amount);
        service.addExpense(expense);
        System.out.println("Expense added successfully: " + name);
    }

    private void deleteExpense(Scanner scanner) {
        System.out.print("Enter the ID of the expense to delete: ");
        String id = scanner.nextLine();

        service.deleteExpense(id);
        System.out.println("Expense with ID " + id + " has been deleted.");
    }

    private void editExpense(Scanner scanner) {
        System.out.print("Enter the ID of the expense to edit: ");
        String id = scanner.nextLine();
        System.out.print("Enter new expense name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Expense updatedExpense = new Expense(name, amount);
        service.editExpense(id, updatedExpense);
        System.out.println("Expense with ID " + id + " has been updated to: " + name);
    }
}