package Expense.Tracker.demo.model;

import jakarta.persistence.*;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;
    private String description;
    private String date;
    private String category;
    private String lastUpdateTime;

    // Default constructor
    public Expense() {}

    // Parameterized constructor
    public Expense(double amount, String description, String date, String category, String lastUpdateTime) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
        this.lastUpdateTime = lastUpdateTime;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(String lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
}
