package com.example.sizgro; // Execution environment ke standard rules ko follow karte hue line build hui hai.

import android.content.Intent; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.os.Bundle; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.view.LayoutInflater; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.view.View; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import android.view.ViewGroup; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.BaseAdapter; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import android.widget.Button; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.text.Editable; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import android.text.TextWatcher; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.EditText; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.GridView; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.widget.ImageView; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.TextView; // Yahan par data initialization ya function call ho raha hai.
import android.widget.Toast; // Execution environment ke standard rules ko follow karte hue line build hui hai.

import androidx.appcompat.app.AlertDialog; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import androidx.appcompat.app.AppCompatActivity; // In variables aur methods se framework ka internal object state maintain ho raha hai.

import com.example.sizgro.models.Product; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import com.google.firebase.auth.FirebaseAuth; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import com.google.firebase.database.DataSnapshot; // Ye syntax system ki base execution me madad kar raha hai.
import com.google.firebase.database.DatabaseError; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import com.google.firebase.database.DatabaseReference; // Program ka control flow yahan par practically manage kiya ja raha hai.
import com.google.firebase.database.FirebaseDatabase; // Program ka control flow yahan par practically manage kiya ja raha hai.
import com.google.firebase.database.ValueEventListener; // Execution environment ke standard rules ko follow karte hue line build hui hai.

import java.util.ArrayList; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import java.util.List; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

public class UserHomeActivity extends AppCompatActivity { // Ye line code execution ka ek basic step perform kar rahi hai.

    private GridView gridViewProducts; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    private ProductAdapter adapter; // Program ka control flow yahan par practically manage kiya ja raha hai.
    private List<Product> productList; // Execution environment ke standard rules ko follow karte hue line build hui hai.
    private List<Product> filteredList; // Ye syntax system ki base execution me madad kar raha hai.
    private DatabaseReference dbRef; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    private EditText etSearch; // Ye line code execution ka ek basic step perform kar rahi hai.
    private TextView tvEmpty; // Ye line code execution ka ek basic step perform kar rahi hai.

    @Override // Yahan par data initialization ya function call ho raha hai.
    protected void onCreate(Bundle savedInstanceState) { // Yahan par data initialization ya function call ho raha hai.
        super.onCreate(savedInstanceState); // Program ka control flow yahan par practically manage kiya ja raha hai.
        setContentView(R.layout.activity_user_home); // Program ka control flow yahan par practically manage kiya ja raha hai.

        gridViewProducts = findViewById(R.id.gridViewProducts); // Ye syntax system ki base execution me madad kar raha hai.
        etSearch = findViewById(R.id.etSearch); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        tvEmpty = findViewById(R.id.tvEmpty); // Execution environment ke standard rules ko follow karte hue line build hui hai.

        productList = new ArrayList<>(); // Ye line code execution ka ek basic step perform kar rahi hai.
        filteredList = new ArrayList<>(); // Ye syntax system ki base execution me madad kar raha hai.
        adapter = new ProductAdapter(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        gridViewProducts.setAdapter(adapter); // Ye syntax system ki base execution me madad kar raha hai.

        dbRef = FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/") // Ye syntax system ki base execution me madad kar raha hai.
                .getReference("products"); // Ye line code execution ka ek basic step perform kar rahi hai.

        findViewById(R.id.btnCart).setOnClickListener(v -> { // Ye syntax system ki base execution me madad kar raha hai.
            Intent cartIntent = new Intent(this, CartActivity.class); // Ye line code execution ka ek basic step perform kar rahi hai.
            startActivity(cartIntent); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        }); // Program ka control flow yahan par practically manage kiya ja raha hai.

        findViewById(R.id.btnLogout).setOnClickListener(v -> { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            FirebaseAuth.getInstance().signOut(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            startActivity(new Intent(this, LoginActivity.class)); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            finish(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        }); // Ye syntax system ki base execution me madad kar raha hai.

        etSearch.addTextChangedListener(new TextWatcher() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            @Override // Program ka control flow yahan par practically manage kiya ja raha hai.
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } // Ye syntax system ki base execution me madad kar raha hai.

            @Override // Execution environment ke standard rules ko follow karte hue line build hui hai.
            public void onTextChanged(CharSequence s, int start, int before, int count) { // Program ka control flow yahan par practically manage kiya ja raha hai.
                filter(s.toString()); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            } // Application codebase ka ek zaroori hissa run ho raha hai is block se.

            @Override // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            public void afterTextChanged(Editable s) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        }); // Ye syntax system ki base execution me madad kar raha hai.

        loadProducts(); // Yahan par data initialization ya function call ho raha hai.

        gridViewProducts.setOnItemClickListener((parent, view, position, id) -> { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            Product product = filteredList.get(position); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            Intent intent = new Intent(this, ProductDetailActivity.class); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            intent.putExtra("productId", product.id); // Program ka control flow yahan par practically manage kiya ja raha hai.
            intent.putExtra("name", product.name); // In variables aur methods se framework ka internal object state maintain ho raha hai.
            intent.putExtra("price", product.price); // Ye syntax system ki base execution me madad kar raha hai.
            intent.putExtra("description", product.description); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            intent.putExtra("category", product.category); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            startActivity(intent); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        }); // Yahan par data initialization ya function call ho raha hai.
    } // Ye syntax system ki base execution me madad kar raha hai.

    private void loadProducts() { // Yahan par data initialization ya function call ho raha hai.
        dbRef.addValueEventListener(new ValueEventListener() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            @Override // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            public void onDataChange(DataSnapshot snapshot) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                productList.clear(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { // Program ka control flow yahan par practically manage kiya ja raha hai.
                    Product product = postSnapshot.getValue(Product.class); // Ye syntax system ki base execution me madad kar raha hai.
                    productList.add(product); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                filter(etSearch.getText().toString()); // Ye syntax system ki base execution me madad kar raha hai.
            } // Ye syntax system ki base execution me madad kar raha hai.

            @Override // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            public void onCancelled(DatabaseError error) { // Program ka control flow yahan par practically manage kiya ja raha hai.
                Toast.makeText(UserHomeActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            } // Yahan par data initialization ya function call ho raha hai.
        }); // Program ka control flow yahan par practically manage kiya ja raha hai.
    } // Application codebase ka ek zaroori hissa run ho raha hai is block se.

    private void filter(String text) { // Program ka control flow yahan par practically manage kiya ja raha hai.
        filteredList.clear(); // Program ka control flow yahan par practically manage kiya ja raha hai.
        for (Product product : productList) { // Ye syntax system ki base execution me madad kar raha hai.
            if (product.name.toLowerCase().contains(text.toLowerCase())) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                filteredList.add(product); // Ye line code execution ka ek basic step perform kar rahi hai.
            } // Program ka control flow yahan par practically manage kiya ja raha hai.
        } // In variables aur methods se framework ka internal object state maintain ho raha hai.
        if (filteredList.isEmpty()) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
            tvEmpty.setVisibility(View.VISIBLE); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        } else { // Program ka control flow yahan par practically manage kiya ja raha hai.
            tvEmpty.setVisibility(View.GONE); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        adapter.notifyDataSetChanged(); // Ye syntax system ki base execution me madad kar raha hai.
    } // Application codebase ka ek zaroori hissa run ho raha hai is block se.

    private class ProductAdapter extends BaseAdapter { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        @Override // Yahan par data initialization ya function call ho raha hai.
        public int getCount() { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return filteredList.size(); // Program ka control flow yahan par practically manage kiya ja raha hai.
        } // Ye line code execution ka ek basic step perform kar rahi hai.

        @Override // Program ka control flow yahan par practically manage kiya ja raha hai.
        public Object getItem(int position) { // Ye line code execution ka ek basic step perform kar rahi hai.
            return filteredList.get(position); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.

        @Override // Yahan par data initialization ya function call ho raha hai.
        public long getItemId(int position) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return position; // Yahan par data initialization ya function call ho raha hai.
        } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

        @Override // Ye syntax system ki base execution me madad kar raha hai.
        public View getView(int position, View convertView, ViewGroup parent) { // Yahan par data initialization ya function call ho raha hai.
            if (convertView == null) { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                convertView = LayoutInflater.from(UserHomeActivity.this).inflate(R.layout.item_product, parent, false); // Ye line code execution ka ek basic step perform kar rahi hai.
            } // Ye syntax system ki base execution me madad kar raha hai.

            Product product = filteredList.get(position); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            TextView tvName = convertView.findViewById(R.id.tvProductName); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            TextView tvPrice = convertView.findViewById(R.id.tvProductPrice); // In variables aur methods se framework ka internal object state maintain ho raha hai.
            TextView btnAddToCart = convertView.findViewById(R.id.btnAddToCart); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            TextView btnBuyNow = convertView.findViewById(R.id.btnBuyNow); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

            tvName.setText(product.name); // Ye syntax system ki base execution me madad kar raha hai.
            tvPrice.setText("$" + String.format("%.2f", product.price)); // Ye syntax system ki base execution me madad kar raha hai.

            btnAddToCart.setOnClickListener(v -> { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                Intent cartIntent = new Intent(UserHomeActivity.this, CartActivity.class); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                cartIntent.putExtra("name", product.name); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                cartIntent.putExtra("price", product.price); // Yahan par data initialization ya function call ho raha hai.
                cartIntent.putExtra("category", product.category); // Yahan par data initialization ya function call ho raha hai.
                startActivity(cartIntent); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                Toast.makeText(UserHomeActivity.this, product.name + " added to basket", Toast.LENGTH_SHORT).show(); // In variables aur methods se framework ka internal object state maintain ho raha hai.
            }); // Ye syntax system ki base execution me madad kar raha hai.

            btnBuyNow.setOnClickListener(v -> { // Yahan par data initialization ya function call ho raha hai.
                Intent checkoutIntent = new Intent(UserHomeActivity.this, CheckoutActivity.class); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                checkoutIntent.putExtra("totalPrice", product.price); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                checkoutIntent.putExtra("itemsSummary", product.name + " (x1)"); // Program ka control flow yahan par practically manage kiya ja raha hai.
                startActivity(checkoutIntent); // Program ka control flow yahan par practically manage kiya ja raha hai.
            }); // Yahan par data initialization ya function call ho raha hai.

            return convertView; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    } // In variables aur methods se framework ka internal object state maintain ho raha hai.
} // Application codebase ka ek zaroori hissa run ho raha hai is block se.