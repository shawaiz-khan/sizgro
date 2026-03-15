package com.example.sizgro; // Ye line code execution ka ek basic step perform kar rahi hai.

import android.content.Intent; // Ye syntax system ki base execution me madad kar raha hai.
import android.os.Bundle; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.view.LayoutInflater; // Yahan par data initialization ya function call ho raha hai.
import android.view.View; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.view.ViewGroup; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import android.widget.BaseAdapter; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.Button; // Ye line code execution ka ek basic step perform kar rahi hai.
import android.widget.ImageView; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.ListView; // Yahan par data initialization ya function call ho raha hai.
import android.widget.TextView; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.Toast; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

import androidx.appcompat.app.AppCompatActivity; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

import com.example.sizgro.models.Product; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.google.firebase.auth.FirebaseAuth; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.google.firebase.database.DataSnapshot; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.google.firebase.database.DatabaseError; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import com.google.firebase.database.DatabaseReference; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.google.firebase.database.FirebaseDatabase; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import com.google.firebase.database.ValueEventListener; // In variables aur methods se framework ka internal object state maintain ho raha hai.

import java.util.ArrayList; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import java.util.List; // Program ka control flow yahan par practically manage kiya ja raha hai.

public class SellerHomeActivity extends AppCompatActivity { // Yahan par data initialization ya function call ho raha hai.

    private ListView listViewInventory; // Ye line code execution ka ek basic step perform kar rahi hai.
    private InventoryAdapter adapter; // Yahan par data initialization ya function call ho raha hai.
    private List<Product> inventoryList; // Ye line code execution ka ek basic step perform kar rahi hai.
    private DatabaseReference dbRef; // Program ka control flow yahan par practically manage kiya ja raha hai.
    private String currentSellerId; // Ye syntax system ki base execution me madad kar raha hai.

    @Override // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    protected void onCreate(Bundle savedInstanceState) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
        super.onCreate(savedInstanceState); // Yahan par data initialization ya function call ho raha hai.
        setContentView(R.layout.activity_seller_home); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        currentSellerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Yahan par data initialization ya function call ho raha hai.
        dbRef = FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/") // Program ka control flow yahan par practically manage kiya ja raha hai.
                .getReference("products"); // Ye syntax system ki base execution me madad kar raha hai.

        listViewInventory = findViewById(R.id.listViewInventory); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        inventoryList = new ArrayList<>(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        adapter = new InventoryAdapter(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        listViewInventory.setAdapter(adapter); // Execution environment ke standard rules ko follow karte hue line build hui hai.

        findViewById(R.id.btnLogout).setOnClickListener(v -> { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            FirebaseAuth.getInstance().signOut(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            startActivity(new Intent(this, LoginActivity.class)); // Yahan par data initialization ya function call ho raha hai.
            finish(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        }); // Yahan par data initialization ya function call ho raha hai.

        findViewById(R.id.btnAddProduct).setOnClickListener(v -> { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            startActivity(new Intent(this, AddEditProductActivity.class)); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        }); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        loadInventory(); // Ye line code execution ka ek basic step perform kar rahi hai.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

    private void loadInventory() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        // Query products where sellerId matches
        dbRef.orderByChild("sellerId").equalTo(currentSellerId) // Yahan par data initialization ya function call ho raha hai.
                .addValueEventListener(new ValueEventListener() { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    @Override // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                    public void onDataChange(DataSnapshot snapshot) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                        inventoryList.clear(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) { // Ye syntax system ki base execution me madad kar raha hai.
                            Product product = postSnapshot.getValue(Product.class); // Program ka control flow yahan par practically manage kiya ja raha hai.
                            inventoryList.add(product); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                        if (inventoryList.isEmpty()) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                            findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                        } else { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                            findViewById(R.id.tvEmpty).setVisibility(View.GONE); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                        } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        adapter.notifyDataSetChanged(); // Yahan par data initialization ya function call ho raha hai.
                    } // Program ka control flow yahan par practically manage kiya ja raha hai.

                    @Override // Yahan par data initialization ya function call ho raha hai.
                    public void onCancelled(DatabaseError error) { // Yahan par data initialization ya function call ho raha hai.
                        Toast.makeText(SellerHomeActivity.this, "Failed to load inventory", Toast.LENGTH_SHORT).show(); // Ye line code execution ka ek basic step perform kar rahi hai.
                    } // Program ka control flow yahan par practically manage kiya ja raha hai.
                }); // Execution environment ke standard rules ko follow karte hue line build hui hai.
    } // Execution environment ke standard rules ko follow karte hue line build hui hai.

    private class InventoryAdapter extends BaseAdapter { // Ye syntax system ki base execution me madad kar raha hai.
        @Override // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        public int getCount() { // Ye syntax system ki base execution me madad kar raha hai.
            return inventoryList.size(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.

        @Override // Ye syntax system ki base execution me madad kar raha hai.
        public Object getItem(int position) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
            return inventoryList.get(position); // Program ka control flow yahan par practically manage kiya ja raha hai.
        } // Program ka control flow yahan par practically manage kiya ja raha hai.

        @Override // Ye line code execution ka ek basic step perform kar rahi hai.
        public long getItemId(int position) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return position; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        } // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        @Override // In variables aur methods se framework ka internal object state maintain ho raha hai.
        public View getView(int position, View convertView, ViewGroup parent) { // Ye syntax system ki base execution me madad kar raha hai.
            if (convertView == null) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                convertView = LayoutInflater.from(SellerHomeActivity.this).inflate(R.layout.item_inventory, parent, // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        false); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

            Product product = inventoryList.get(position); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            TextView tvName = convertView.findViewById(R.id.tvName); // Ye line code execution ka ek basic step perform kar rahi hai.
            TextView tvPrice = convertView.findViewById(R.id.tvPrice); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            TextView tvCategory = convertView.findViewById(R.id.tvCategory); // In variables aur methods se framework ka internal object state maintain ho raha hai.
            ImageView btnEdit = convertView.findViewById(R.id.btnEdit); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

            tvName.setText(product.name); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            tvPrice.setText("$" + String.format("%.2f", product.price)); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            tvCategory.setText(product.category); // Ye syntax system ki base execution me madad kar raha hai.

            btnEdit.setOnClickListener(v -> { // Yahan par data initialization ya function call ho raha hai.
                Intent intent = new Intent(SellerHomeActivity.this, AddEditProductActivity.class); // Program ka control flow yahan par practically manage kiya ja raha hai.
                intent.putExtra("productId", product.id); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                intent.putExtra("name", product.name); // Ye line code execution ka ek basic step perform kar rahi hai.
                intent.putExtra("price", product.price); // Yahan par data initialization ya function call ho raha hai.
                intent.putExtra("description", product.description); // Ye syntax system ki base execution me madad kar raha hai.
                intent.putExtra("category", product.category); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                startActivity(intent); // Ye line code execution ka ek basic step perform kar rahi hai.
            }); // Execution environment ke standard rules ko follow karte hue line build hui hai.

            return convertView; // Program ka control flow yahan par practically manage kiya ja raha hai.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.
    } // Execution environment ke standard rules ko follow karte hue line build hui hai.
} // Program ka control flow yahan par practically manage kiya ja raha hai.
