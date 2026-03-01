package com.example.sizgro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SellerHomeActivity extends AppCompatActivity {

    private ListView listViewInventory;
    private InventoryAdapter adapter;
    private List<Product> inventoryList;
    private DatabaseReference dbRef;
    private String currentSellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        currentSellerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/")
                .getReference("products");

        listViewInventory = findViewById(R.id.listViewInventory);
        inventoryList = new ArrayList<>();
        adapter = new InventoryAdapter();
        listViewInventory.setAdapter(adapter);

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        findViewById(R.id.btnAddProduct).setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditProductActivity.class));
        });

        loadInventory();
    }

    private void loadInventory() {
        // Query products where sellerId matches
        dbRef.orderByChild("sellerId").equalTo(currentSellerId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        inventoryList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Product product = postSnapshot.getValue(Product.class);
                            inventoryList.add(product);
                        }
                        if (inventoryList.isEmpty()) {
                            findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.tvEmpty).setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(SellerHomeActivity.this, "Failed to load inventory", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class InventoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return inventoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return inventoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SellerHomeActivity.this).inflate(R.layout.item_inventory, parent,
                        false);
            }

            Product product = inventoryList.get(position);
            TextView tvName = convertView.findViewById(R.id.tvName);
            TextView tvPrice = convertView.findViewById(R.id.tvPrice);
            TextView tvCategory = convertView.findViewById(R.id.tvCategory);
            ImageView btnEdit = convertView.findViewById(R.id.btnEdit);

            tvName.setText(product.name);
            tvPrice.setText("$" + String.format("%.2f", product.price));
            tvCategory.setText(product.category);

            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(SellerHomeActivity.this, AddEditProductActivity.class);
                intent.putExtra("productId", product.id);
                intent.putExtra("name", product.name);
                intent.putExtra("price", product.price);
                intent.putExtra("description", product.description);
                intent.putExtra("category", product.category);
                startActivity(intent);
            });

            return convertView;
        }
    }
}
