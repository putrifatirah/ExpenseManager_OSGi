package com.example.expensetracker.impl;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private final MongoDatabase database = mongoClient.getDatabase("expense_tracker");
    private final MongoCollection<Document> collection = database.getCollection("expenses");

    @Override
    public void addExpense(Expense expense) {
        Document doc = new Document("id", expense.getId())
                .append("name", expense.getName())
                .append("amount", expense.getAmount())
                .append("date", expense.getDate());
        collection.insertOne(doc);
    }

    @Override
    public void editExpense(String id, Expense updatedExpense) {
        collection.updateOne(new Document("id", id),
                new Document("$set", new Document("name", updatedExpense.getName())
                        .append("amount", updatedExpense.getAmount())
                        .append("date", updatedExpense.getDate())));
    }

    @Override
    public void deleteExpense(String id) {
        collection.deleteOne(new Document("id", id));
    }

    @Override
    public List<Expense> viewExpenses() {
        List<Expense> result = new ArrayList<>();
        collection.find().forEach(doc -> result.add(new Expense(
                doc.getString("id"),
                doc.getString("name"),
                doc.getDouble("amount"),
                doc.getString("date")
        )));
        return result;
    }

    @Override
    public double calculateTotal() {
        return collection.find().into(new ArrayList<>())
                .stream()
                .mapToDouble(doc -> doc.getDouble("amount"))
                .sum();
    }
}
