package com.example.sizgro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sizgro.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    private GridView gridViewProducts;
    private ProductAdapter adapter;
    private List<Product> productList;
    private List<Product> filteredList;
    private DatabaseReference dbRef;
    private EditText etSearch;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        gridViewProducts = findViewById(R.id.gridViewProducts);
        etSearch = findViewById(R.id.etSearch);
        tvEmpty = findViewById(R.id.tvEmpty);

        productList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new ProductAdapter();
        gridViewProducts.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/")
                .getReference("products");

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            Intent cartIntent = new Intent(this, CartActivity.class);
            startActivity(cartIntent);
        });

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadProducts();

        gridViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product product = filteredList.get(position);
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("productId", product.id);
            intent.putExtra("name", product.name);
            intent.putExtra("price", product.price);
            intent.putExtra("description", product.description);
            intent.putExtra("category", product.category);
            startActivity(intent);
        });
    }

    private void loadProducts() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                filter(etSearch.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserHomeActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        for (Product product : productList) {
            if (product.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }
        if (filteredList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private class ProductAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return filteredList.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(UserHomeActivity.this).inflate(R.layout.item_product, parent, false);
            }

            Product product = filteredList.get(position);
            TextView tvName = convertView.findViewById(R.id.tvProductName);
            TextView tvPrice = convertView.findViewById(R.id.tvProductPrice);
            TextView btnAddToCart = convertView.findViewById(R.id.btnAddToCart);
            TextView btnBuyNow = convertView.findViewById(R.id.btnBuyNow);

            tvName.setText(product.name);
            tvPrice.setText("$" + String.format("%.2f", product.price));

            btnAddToCart.setOnClickListener(v -> {
                Intent cartIntent = new Intent(UserHomeActivity.this, CartActivity.class);
                cartIntent.putExtra("name", product.name);
                cartIntent.putExtra("price", product.price);
                cartIntent.putExtra("category", product.category);
                startActivity(cartIntent);
                Toast.makeText(UserHomeActivity.this, product.name + " added to basket", Toast.LENGTH_SHORT).show();
            });

            btnBuyNow.setOnClickListener(v -> {
                Intent checkoutIntent = new Intent(UserHomeActivity.this, CheckoutActivity.class);
                checkoutIntent.putExtra("totalPrice", product.price);
                checkoutIntent.putExtra("itemsSummary", product.name + " (x1)");
                startActivity(checkoutIntent);
            });

            return convertView;
        }
    }
}