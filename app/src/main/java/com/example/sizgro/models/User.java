package com.example.sizgro.models; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

public class User { // Execution environment ke standard rules ko follow karte hue line build hui hai.
    public String id; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    public String email; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    public String role; // "seller" or "user" // Execution environment ke standard rules ko follow karte hue line build hui hai.

    public User() {} // Yahan par data initialization ya function call ho raha hai.

    public User(String id, String email, String role) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
        this.id = id; // In variables aur methods se framework ka internal object state maintain ho raha hai.
        this.email = email; // Yahan par data initialization ya function call ho raha hai.
        this.role = role; // Yahan par data initialization ya function call ho raha hai.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
} // Program ka control flow yahan par practically manage kiya ja raha hai.
