package com.example.expensetracker.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.example.expensetracker.api.Expense;
import com.example.expensetracker.api.ExpenseService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Component(service = ExpenseService.class, immediate = true)
public class ExpenseServiceImpl implements ExpenseService {
	private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Activate
    public void activate() {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("expense_tracker");
            collection = database.getCollection("expenses");
        } catch (Exception e) {
            System.err.println("Error initializing MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Deactivate
    public void deactivate() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    
    @Override
    public void addExpense(Expense expense) {
        Document doc = new Document("name", expense.getName())
                .append("amount", expense.getAmount())
                .append("date", expense.getMongoDate());  // Use Mongo-compatible date
        
        // MongoDB will automatically generate an _id field
        collection.insertOne(doc);
    }

    @Override
    public void editExpense(String id, Expense updatedExpense) {
        ObjectId objectId = new ObjectId(id);  // Convert string id to ObjectId
        collection.updateOne(new Document("_id", objectId),
                new Document("$set", new Document("name", updatedExpense.getName())
                        .append("amount", updatedExpense.getAmount())
                        .append("date", updatedExpense.getMongoDate())));  // Update Mongo-compatible date
    }

    @Override
    public void deleteExpense(String id) {
        ObjectId objectId = new ObjectId(id);  // Convert string id to ObjectId
        collection.deleteOne(new Document("_id", objectId));
    }

    @Override
    public List<Expense> viewExpenses() {
        List<Expense> result = new ArrayList<>();
        collection.find().forEach(doc -> result.add(new Expense(
                doc.getObjectId("_id"),  // Retrieve MongoDB's generated ObjectId
                doc.getString("name"),
                doc.getDouble("amount"),
                doc.getDate("date").toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime()  // Convert MongoDB Date to LocalDateTime
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
