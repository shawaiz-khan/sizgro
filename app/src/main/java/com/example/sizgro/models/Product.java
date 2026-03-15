package com.example.sizgro.models; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

public class Product { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    public String id; // Yahan par data initialization ya function call ho raha hai.
    public String name; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    public String description; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    public double price; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    public String category; // Program ka control flow yahan par practically manage kiya ja raha hai.
    public String sellerId; // Ye line code execution ka ek basic step perform kar rahi hai.
    public int quantity = 1; // Ye line code execution ka ek basic step perform kar rahi hai.

    public Product() { // Program ka control flow yahan par practically manage kiya ja raha hai.
        // Default constructor required for calls to
        // DataSnapshot.getValue(Product.class)
    } // In variables aur methods se framework ka internal object state maintain ho raha hai.

    public Product(String id, String name, String description, double price, String category, String sellerId) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
        this.id = id; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        this.name = name; // Ye syntax system ki base execution me madad kar raha hai.
        this.description = description; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        this.price = price; // Yahan par data initialization ya function call ho raha hai.
        this.category = category; // Execution environment ke standard rules ko follow karte hue line build hui hai.
        this.sellerId = sellerId; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        this.quantity = 1; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
} // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
