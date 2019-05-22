package com.olabode33.smallbooks.Model;

import com.google.firebase.database.Exclude;

public class Category {
    @Exclude
    private String key;
    private String category;
    private String type;

    public Category() {
    }

    public Category(String category, String type) {
        this.category = category;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
