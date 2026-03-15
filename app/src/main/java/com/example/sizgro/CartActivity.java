package com.example.sizgro; // Ye line code execution ka ek basic step perform kar rahi hai.

import android.content.Intent; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.os.Bundle; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.ArrayAdapter; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.widget.Button; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.widget.EditText; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.widget.Spinner; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import android.widget.TextView; // Yahan par data initialization ya function call ho raha hai.
import android.widget.Toast; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

import androidx.appcompat.app.AlertDialog; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import androidx.appcompat.app.AppCompatActivity; // Ye line code execution ka ek basic step perform kar rahi hai.

import android.view.LayoutInflater; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.view.View; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.view.ViewGroup; // Yahan par data initialization ya function call ho raha hai.
import android.widget.BaseAdapter; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.ImageView; // Yahan par data initialization ya function call ho raha hai.
import android.widget.ListView; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.example.sizgro.models.Product; // Ye line code execution ka ek basic step perform kar rahi hai.
import java.util.ArrayList; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import java.util.List; // Execution environment ke standard rules ko follow karte hue line build hui hai.

public class CartActivity extends AppCompatActivity { // Program ka control flow yahan par practically manage kiya ja raha hai.

    private ListView listViewCart; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    private TextView tvCartTotal, btnBack; // In variables aur methods se framework ka internal object state maintain ho raha hai.
    private Button btnGoToCheckout; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    private static List<Product> cartItems = new ArrayList<>(); // Ye line code execution ka ek basic step perform kar rahi hai.
    private double totalPrice = 0.0; // Yahan par data initialization ya function call ho raha hai.

    @Override // Ye syntax system ki base execution me madad kar raha hai.
    protected void onCreate(Bundle savedInstanceState) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        super.onCreate(savedInstanceState); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        setContentView(R.layout.activity_cart); // Execution environment ke standard rules ko follow karte hue line build hui hai.

        listViewCart = findViewById(R.id.listViewCart); // Program ka control flow yahan par practically manage kiya ja raha hai.
        tvCartTotal = findViewById(R.id.tvCartTotal); // Program ka control flow yahan par practically manage kiya ja raha hai.
        btnBack = findViewById(R.id.btnBack); // Ye syntax system ki base execution me madad kar raha hai.
        btnGoToCheckout = findViewById(R.id.btnGoToCheckout); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        // Mocking: Get the product that was just "added"
        String name = getIntent().getStringExtra("name"); // Ye line code execution ka ek basic step perform kar rahi hai.
        double price = getIntent().getDoubleExtra("price", 0.0); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        String category = getIntent().getStringExtra("category"); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        if (name != null) { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            boolean found = false; // Execution environment ke standard rules ko follow karte hue line build hui hai.
            for (Product p : cartItems) { // Ye syntax system ki base execution me madad kar raha hai.
                if (p.name.equals(name)) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    p.quantity++; // Yahan par data initialization ya function call ho raha hai.
                    found = true; // Execution environment ke standard rules ko follow karte hue line build hui hai.
                    break; // Ye syntax system ki base execution me madad kar raha hai.
                } // Yahan par data initialization ya function call ho raha hai.
            } // Ye syntax system ki base execution me madad kar raha hai.
            if (!found) { // Program ka control flow yahan par practically manage kiya ja raha hai.
                cartItems.add(new Product("0", name, "", price, category, "")); // Yahan par data initialization ya function call ho raha hai.
            } // Yahan par data initialization ya function call ho raha hai.
            // Avoid duplicate additions if the user just rotatated the screen
            getIntent().removeExtra("name"); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

        calculateTotal(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        updateUI(); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        listViewCart.setAdapter(new CartAdapter()); // Application codebase ka ek zaroori hissa run ho raha hai is block se.

        btnBack.setOnClickListener(v -> finish()); // Program ka control flow yahan par practically manage kiya ja raha hai.
        findViewById(R.id.btnContinueShopping).setOnClickListener(v -> finish()); // Ye line code execution ka ek basic step perform kar rahi hai.

        btnGoToCheckout.setOnClickListener(v -> { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            if (cartItems.isEmpty()) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show(); // Ye syntax system ki base execution me madad kar raha hai.
            } else { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class); // Program ka control flow yahan par practically manage kiya ja raha hai.
                checkoutIntent.putExtra("totalPrice", totalPrice); // Ye syntax system ki base execution me madad kar raha hai.

                StringBuilder summary = new StringBuilder(); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                for (Product p : cartItems) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    summary.append(p.name).append(" (x").append(p.quantity).append("), "); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                } // Program ka control flow yahan par practically manage kiya ja raha hai.
                // Remove trailing comma
                String finalSummary = summary.length() > 2 ? summary.substring(0, summary.length() - 2) : "Items"; // Yahan par data initialization ya function call ho raha hai.
                checkoutIntent.putExtra("itemsSummary", finalSummary); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

                startActivity(checkoutIntent); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } // Execution environment ke standard rules ko follow karte hue line build hui hai.
        }); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    } // Ye syntax system ki base execution me madad kar raha hai.

    private void calculateTotal() { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        totalPrice = 0.0; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        for (Product p : cartItems) { // Ye syntax system ki base execution me madad kar raha hai.
            totalPrice += (p.price * p.quantity); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        } // Yahan par data initialization ya function call ho raha hai.
    } // Program ka control flow yahan par practically manage kiya ja raha hai.

    private void updateUI() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        if (cartItems.isEmpty()) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            findViewById(R.id.llEmptyCart).setVisibility(View.VISIBLE); // Program ka control flow yahan par practically manage kiya ja raha hai.
            findViewById(R.id.llCartContent).setVisibility(View.GONE); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            findViewById(R.id.bottomBar).setVisibility(View.GONE); // Yahan par data initialization ya function call ho raha hai.
        } else { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            findViewById(R.id.llEmptyCart).setVisibility(View.GONE); // Program ka control flow yahan par practically manage kiya ja raha hai.
            findViewById(R.id.llCartContent).setVisibility(View.VISIBLE); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            findViewById(R.id.bottomBar).setVisibility(View.VISIBLE); // Ye line code execution ka ek basic step perform kar rahi hai.
            tvCartTotal.setText("$" + String.format("%.2f", totalPrice)); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        } // Yahan par data initialization ya function call ho raha hai.
    } // Ye line code execution ka ek basic step perform kar rahi hai.

    public static void clearCart() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        cartItems.clear(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
    } // Yahan par data initialization ya function call ho raha hai.

    private class CartAdapter extends BaseAdapter { // Program ka control flow yahan par practically manage kiya ja raha hai.
        @Override // Ye syntax system ki base execution me madad kar raha hai.
        public int getCount() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            return cartItems.size(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.

        @Override // Execution environment ke standard rules ko follow karte hue line build hui hai.
        public Object getItem(int position) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return cartItems.get(position); // Yahan par data initialization ya function call ho raha hai.
        } // Yahan par data initialization ya function call ho raha hai.

        @Override // Program ka control flow yahan par practically manage kiya ja raha hai.
        public long getItemId(int position) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            return position; // Ye line code execution ka ek basic step perform kar rahi hai.
        } // Yahan par data initialization ya function call ho raha hai.

        @Override // In variables aur methods se framework ka internal object state maintain ho raha hai.
        public View getView(int position, View convertView, ViewGroup parent) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            if (convertView == null) { // Yahan par data initialization ya function call ho raha hai.
                convertView = LayoutInflater.from(CartActivity.this).inflate(R.layout.item_cart, parent, false); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            Product item = cartItems.get(position); // Program ka control flow yahan par practically manage kiya ja raha hai.
            TextView tvName = convertView.findViewById(R.id.tvCartProductName); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            TextView tvPrice = convertView.findViewById(R.id.tvCartProductPrice); // Ye line code execution ka ek basic step perform kar rahi hai.
            TextView tvCat = convertView.findViewById(R.id.tvCartProductCategory); // Ye line code execution ka ek basic step perform kar rahi hai.
            TextView tvQty = convertView.findViewById(R.id.tvCartProductQty); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

            tvName.setText(item.name); // Program ka control flow yahan par practically manage kiya ja raha hai.
            tvPrice.setText("$" + String.format("%.2f", item.price * item.quantity)); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            tvCat.setText(item.category); // Yahan par data initialization ya function call ho raha hai.
            tvQty.setText("Qty: " + item.quantity); // Ye syntax system ki base execution me madad kar raha hai.

            return convertView; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
} // Program ka control flow yahan par practically manage kiya ja raha hai.
