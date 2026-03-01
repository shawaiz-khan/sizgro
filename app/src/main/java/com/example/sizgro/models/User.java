package com.example.sizgro.models;

public class User {
    public String id;
    public String email;
    public String role; // "seller" or "user"

    public User() {}

    public User(String id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
