package com.olabode33.smallbooks.Model;

import com.google.firebase.database.Exclude;

public class Summary {
    @Exclude
    private String key;
    private double expenses;
    private double income;
    private double balance;

    public Summary() {
    }

    public Summary(double expenses, double income) {
        this.expenses = expenses;
        this.income = income;
        this.balance = income - expenses;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
