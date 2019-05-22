package com.olabode33.smallbooks.Model;

import com.google.firebase.database.Exclude;

public class SummaryByCategory {
    @Exclude
    private String key;
    private String category;
    private double amount;

    public SummaryByCategory() {
    }

    public SummaryByCategory(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
