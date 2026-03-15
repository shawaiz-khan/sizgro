package com.example.sizgro; // In variables aur methods se framework ka internal object state maintain ho raha hai.

import android.content.Intent; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import android.os.Bundle; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.Button; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.ImageView; // Ye syntax system ki base execution me madad kar raha hai.
import android.widget.TextView; // Ye syntax system ki base execution me madad kar raha hai.
import android.widget.Toast; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

import androidx.appcompat.app.AppCompatActivity; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

public class ProductDetailActivity extends AppCompatActivity { // Ye line code execution ka ek basic step perform kar rahi hai.

    private TextView tvName, tvPrice, tvDescription, tvCategory; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    private ImageView ivProduct; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    private Button btnAddToCart; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

    @Override // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    protected void onCreate(Bundle savedInstanceState) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        super.onCreate(savedInstanceState); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        setContentView(R.layout.activity_product_detail); // Program ka control flow yahan par practically manage kiya ja raha hai.

        tvName = findViewById(R.id.tvName); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        tvPrice = findViewById(R.id.tvPrice); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        tvDescription = findViewById(R.id.tvDescription); // Yahan par data initialization ya function call ho raha hai.
        tvCategory = findViewById(R.id.tvCategory); // Ye line code execution ka ek basic step perform kar rahi hai.
        ivProduct = findViewById(R.id.ivProduct); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        btnAddToCart = findViewById(R.id.btnAddToCart); // Program ka control flow yahan par practically manage kiya ja raha hai.
        Button btnBuyNow = findViewById(R.id.btnBuyNow); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        // Get data from Intent
        String name = getIntent().getStringExtra("name"); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        double price = getIntent().getDoubleExtra("price", 0); // Program ka control flow yahan par practically manage kiya ja raha hai.
        String description = getIntent().getStringExtra("description"); // Program ka control flow yahan par practically manage kiya ja raha hai.
        String category = getIntent().getStringExtra("category"); // Ye syntax system ki base execution me madad kar raha hai.

        tvName.setText(name); // Ye syntax system ki base execution me madad kar raha hai.
        tvPrice.setText("$" + String.format("%.2f", price)); // Program ka control flow yahan par practically manage kiya ja raha hai.
        tvDescription.setText(description); // Yahan par data initialization ya function call ho raha hai.
        tvCategory.setText(category); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        findViewById(R.id.btnBack).setOnClickListener(v -> finish()); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        btnAddToCart.setOnClickListener(v -> { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            Intent cartIntent = new Intent(ProductDetailActivity.this, CartActivity.class); // Ye line code execution ka ek basic step perform kar rahi hai.
            cartIntent.putExtra("name", name); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            cartIntent.putExtra("price", price); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            cartIntent.putExtra("category", category); // Ye syntax system ki base execution me madad kar raha hai.
            startActivity(cartIntent); // Ye syntax system ki base execution me madad kar raha hai.
        }); // Ye line code execution ka ek basic step perform kar rahi hai.

        btnBuyNow.setOnClickListener(v -> { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            Intent checkoutIntent = new Intent(ProductDetailActivity.this, CheckoutActivity.class); // Ye line code execution ka ek basic step perform kar rahi hai.
            checkoutIntent.putExtra("totalPrice", price); // In variables aur methods se framework ka internal object state maintain ho raha hai.
            checkoutIntent.putExtra("itemsSummary", name + " (x1)"); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            startActivity(checkoutIntent); // Program ka control flow yahan par practically manage kiya ja raha hai.
        }); // Ye line code execution ka ek basic step perform kar rahi hai.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
} // In variables aur methods se framework ka internal object state maintain ho raha hai.
