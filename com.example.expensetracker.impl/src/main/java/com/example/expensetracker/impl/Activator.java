/*package com.example.expensetracker.impl;

import com.example.expensetracker.api.ExpenseService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) {
        // Create an instance of ExpenseServiceImpl
        ExpenseService service = new ExpenseServiceImpl();

        // Register the Main class as a Gogo shell command provider
        Main mainCommand = new Main(service);
        context.registerService(Main.class.getName(), mainCommand, null);
        System.out.println("Expense tracker commands are now available in the Gogo shell.");
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Expense tracker bundle stopped.");
    }
}*/
package com.example.expensetracker.impl;

import com.example.expensetracker.api.ExpenseService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) {
        // Create an instance of ExpenseServiceImpl
        ExpenseService service = new ExpenseServiceImpl();

        // Register the Main class as a Gogo shell command provider
        Main mainCommand = new Main(service);
        context.registerService(Main.class.getName(), mainCommand, null);

        System.out.println("Expense tracker commands are now available in the Gogo shell.");
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Expense tracker bundle stopped.");
    }
}
