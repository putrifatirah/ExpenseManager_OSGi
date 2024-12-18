package com.example.expensetracker.impl;

import com.example.expensetracker.api.ExpenseService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    private ExpenseService service;
    private Main mainUI;

    @Override
    public void start(BundleContext context) {
        try {
            // Initialize ExpenseService implementation
            service = new ExpenseServiceImpl();

            // Start the Expense Manager menu-based UI
            System.out.println("Starting Expense Manager...");
            mainUI = new Main(service);
            mainUI.start();

            // Register the ExpenseService for OSGi framework
            context.registerService(ExpenseService.class.getName(), service, null);
            System.out.println("Expense tracker started and service registered successfully.");
        } catch (Exception e) {
            System.err.println("Error starting the Expense Manager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Expense Manager...");
        service = null; // Clean up service reference
        mainUI = null;  // Clean up UI reference
        System.out.println("Expense tracker bundle stopped.");
    }
}
