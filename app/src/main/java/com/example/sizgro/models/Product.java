package com.example.sizgro.models;

public class Product {
    public String id;
    public String name;
    public String description;
    public double price;
    public String category;
    public String sellerId;
    public int quantity = 1;

    public Product() {
        // Default constructor required for calls to
        // DataSnapshot.getValue(Product.class)
    }

    public Product(String id, String name, String description, double price, String category, String sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.sellerId = sellerId;
        this.quantity = 1;
    }
}
