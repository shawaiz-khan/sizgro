package com.example.sizgro; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

import android.os.Bundle; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.view.View; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import android.widget.ArrayAdapter; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.widget.Button; // Ye line code execution ka ek basic step perform kar rahi hai.
import android.widget.EditText; // Ye syntax system ki base execution me madad kar raha hai.
import android.widget.Spinner; // Ye syntax system ki base execution me madad kar raha hai.
import android.widget.TextView; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.Toast; // Yahan par data initialization ya function call ho raha hai.

import androidx.appcompat.app.AlertDialog; // Ye syntax system ki base execution me madad kar raha hai.
import androidx.appcompat.app.AppCompatActivity; // In variables aur methods se framework ka internal object state maintain ho raha hai.

import com.example.sizgro.models.Product; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import com.google.firebase.auth.FirebaseAuth; // Program ka control flow yahan par practically manage kiya ja raha hai.
import com.google.firebase.database.DatabaseReference; // Ye syntax system ki base execution me madad kar raha hai.
import com.google.firebase.database.FirebaseDatabase; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

public class AddEditProductActivity extends AppCompatActivity { // Ye line code execution ka ek basic step perform kar rahi hai.

    private EditText etName, etPrice, etDescription; // Execution environment ke standard rules ko follow karte hue line build hui hai.
    private Spinner spinnerCategory; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    private Button btnSave, btnDelete; // In variables aur methods se framework ka internal object state maintain ho raha hai.
    private TextView tvTitle; // Ye line code execution ka ek basic step perform kar rahi hai.
    private String productId = null; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    private DatabaseReference dbRef; // Ye syntax system ki base execution me madad kar raha hai.
    private String sellerId; // In variables aur methods se framework ka internal object state maintain ho raha hai.

    @Override // Ye syntax system ki base execution me madad kar raha hai.
    protected void onCreate(Bundle savedInstanceState) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        super.onCreate(savedInstanceState); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        setContentView(R.layout.activity_add_edit_product); // Program ka control flow yahan par practically manage kiya ja raha hai.

        dbRef = FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/") // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                .getReference("products"); // Ye line code execution ka ek basic step perform kar rahi hai.
        sellerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Ye line code execution ka ek basic step perform kar rahi hai.

        etName = findViewById(R.id.etName); // Ye line code execution ka ek basic step perform kar rahi hai.
        etPrice = findViewById(R.id.etPrice); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        etDescription = findViewById(R.id.etDescription); // Ye syntax system ki base execution me madad kar raha hai.
        spinnerCategory = findViewById(R.id.spinnerCategory); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        btnSave = findViewById(R.id.btnSave); // Yahan par data initialization ya function call ho raha hai.
        btnDelete = findViewById(R.id.btnDelete); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        tvTitle = findViewById(R.id.tvTitle); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        // Setup Categories Spinner
        String[] categories = { "Fruits", "Vegetables", "Dairy", "Bakery", "Meat", "Beverages" }; // Ye syntax system ki base execution me madad kar raha hai.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        spinnerCategory.setAdapter(adapter); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

        // Check if editing
        if (getIntent().hasExtra("productId")) { // Program ka control flow yahan par practically manage kiya ja raha hai.
            productId = getIntent().getStringExtra("productId"); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            etName.setText(getIntent().getStringExtra("name")); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            etPrice.setText(String.valueOf(getIntent().getDoubleExtra("price", 0))); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            etDescription.setText(getIntent().getStringExtra("description")); // Program ka control flow yahan par practically manage kiya ja raha hai.

            // Set spinner selection
            String category = getIntent().getStringExtra("category"); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            for (int i = 0; i < categories.length; i++) { // Ye syntax system ki base execution me madad kar raha hai.
                if (categories[i].equals(category)) { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                    spinnerCategory.setSelection(i); // Program ka control flow yahan par practically manage kiya ja raha hai.
                    break; // Ye line code execution ka ek basic step perform kar rahi hai.
                } // Ye line code execution ka ek basic step perform kar rahi hai.
            } // In variables aur methods se framework ka internal object state maintain ho raha hai.

            tvTitle.setText("Edit Product"); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            btnDelete.setVisibility(View.VISIBLE); // Program ka control flow yahan par practically manage kiya ja raha hai.
        } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

        btnSave.setOnClickListener(v -> saveProduct()); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        btnDelete.setOnClickListener(v -> deleteProduct()); // Ye line code execution ka ek basic step perform kar rahi hai.
    } // Execution environment ke standard rules ko follow karte hue line build hui hai.

    private void saveProduct() { // Execution environment ke standard rules ko follow karte hue line build hui hai.
        String name = etName.getText().toString().trim(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        String priceStr = etPrice.getText().toString().trim(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        String description = etDescription.getText().toString().trim(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        String category = spinnerCategory.getSelectedItem().toString(); // Program ka control flow yahan par practically manage kiya ja raha hai.

        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return; // Ye line code execution ka ek basic step perform kar rahi hai.
        } // Ye line code execution ka ek basic step perform kar rahi hai.

        double price = Double.parseDouble(priceStr); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        if (productId == null) { // Yahan par data initialization ya function call ho raha hai.
            // New Product
            productId = dbRef.push().getKey(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        } // Program ka control flow yahan par practically manage kiya ja raha hai.

        Product product = new Product(productId, name, description, price, category, sellerId); // Program ka control flow yahan par practically manage kiya ja raha hai.
        dbRef.child(productId).setValue(product).addOnCompleteListener(task -> { // Ye line code execution ka ek basic step perform kar rahi hai.
            if (task.isSuccessful()) { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                Toast.makeText(this, "Product Saved Successfully", Toast.LENGTH_SHORT).show(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                finish(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } else { // Execution environment ke standard rules ko follow karte hue line build hui hai.
                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Yahan par data initialization ya function call ho raha hai.
            } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        }); // Ye line code execution ka ek basic step perform kar rahi hai.
    } // Yahan par data initialization ya function call ho raha hai.

    private void deleteProduct() { // Execution environment ke standard rules ko follow karte hue line build hui hai.
        new AlertDialog.Builder(this) // Ye syntax system ki base execution me madad kar raha hai.
                .setTitle("Delete Product") // Execution environment ke standard rules ko follow karte hue line build hui hai.
                .setMessage("Are you sure you want to delete this product?") // Execution environment ke standard rules ko follow karte hue line build hui hai.
                .setPositiveButton("Yes", (dialog, which) -> { // Program ka control flow yahan par practically manage kiya ja raha hai.
                    dbRef.child(productId).removeValue().addOnCompleteListener(task -> { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        if (task.isSuccessful()) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                            Toast.makeText(this, "Product Deleted", Toast.LENGTH_SHORT).show(); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                            finish(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                        } else { // Ye line code execution ka ek basic step perform kar rahi hai.
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT) // Execution environment ke standard rules ko follow karte hue line build hui hai.
                                    .show(); // Program ka control flow yahan par practically manage kiya ja raha hai.
                        } // Execution environment ke standard rules ko follow karte hue line build hui hai.
                    }); // Yahan par data initialization ya function call ho raha hai.
                }) // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                .setNegativeButton("No", null) // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                .show(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    } // Program ka control flow yahan par practically manage kiya ja raha hai.
} // Yahan par data initialization ya function call ho raha hai.
