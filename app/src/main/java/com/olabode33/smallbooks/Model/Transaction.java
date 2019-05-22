package com.olabode33.smallbooks.Model;

import com.google.firebase.database.Exclude;

public class Transaction {
    @Exclude
    private String key;
    private String type;
    private String date;
    private double amount;
    private String memo;
    private String category;

    public Transaction() {
    }

    public Transaction(String type, String date, double amount, String memo, String category) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
