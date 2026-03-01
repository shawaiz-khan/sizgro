package com.example.sizgro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDescription, tvCategory;
    private ImageView ivProduct;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvCategory = findViewById(R.id.tvCategory);
        ivProduct = findViewById(R.id.ivProduct);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBuyNow = findViewById(R.id.btnBuyNow);

        // Get data from Intent
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0);
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        tvName.setText(name);
        tvPrice.setText("$" + String.format("%.2f", price));
        tvDescription.setText(description);
        tvCategory.setText(category);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        btnAddToCart.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ProductDetailActivity.this, CartActivity.class);
            cartIntent.putExtra("name", name);
            cartIntent.putExtra("price", price);
            cartIntent.putExtra("category", category);
            startActivity(cartIntent);
        });

        btnBuyNow.setOnClickListener(v -> {
            Intent checkoutIntent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
            checkoutIntent.putExtra("totalPrice", price);
            checkoutIntent.putExtra("itemsSummary", name + " (x1)");
            startActivity(checkoutIntent);
        });
    }
}
