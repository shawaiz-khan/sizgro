package com.example.sizgro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.sizgro.models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private TextView tvCartTotal, btnBack;
    private Button btnGoToCheckout;
    private static List<Product> cartItems = new ArrayList<>();
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);
        tvCartTotal = findViewById(R.id.tvCartTotal);
        btnBack = findViewById(R.id.btnBack);
        btnGoToCheckout = findViewById(R.id.btnGoToCheckout);

        // Mocking: Get the product that was just "added"
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String category = getIntent().getStringExtra("category");

        if (name != null) {
            boolean found = false;
            for (Product p : cartItems) {
                if (p.name.equals(name)) {
                    p.quantity++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                cartItems.add(new Product("0", name, "", price, category, ""));
            }
            // Avoid duplicate additions if the user just rotatated the screen
            getIntent().removeExtra("name");
        }

        calculateTotal();
        updateUI();

        listViewCart.setAdapter(new CartAdapter());

        btnBack.setOnClickListener(v -> finish());
        findViewById(R.id.btnContinueShopping).setOnClickListener(v -> finish());

        btnGoToCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra("totalPrice", totalPrice);

                StringBuilder summary = new StringBuilder();
                for (Product p : cartItems) {
                    summary.append(p.name).append(" (x").append(p.quantity).append("), ");
                }
                // Remove trailing comma
                String finalSummary = summary.length() > 2 ? summary.substring(0, summary.length() - 2) : "Items";
                checkoutIntent.putExtra("itemsSummary", finalSummary);

                startActivity(checkoutIntent);
            }
        });
    }

    private void calculateTotal() {
        totalPrice = 0.0;
        for (Product p : cartItems) {
            totalPrice += (p.price * p.quantity);
        }
    }

    private void updateUI() {
        if (cartItems.isEmpty()) {
            findViewById(R.id.llEmptyCart).setVisibility(View.VISIBLE);
            findViewById(R.id.llCartContent).setVisibility(View.GONE);
            findViewById(R.id.bottomBar).setVisibility(View.GONE);
        } else {
            findViewById(R.id.llEmptyCart).setVisibility(View.GONE);
            findViewById(R.id.llCartContent).setVisibility(View.VISIBLE);
            findViewById(R.id.bottomBar).setVisibility(View.VISIBLE);
            tvCartTotal.setText("$" + String.format("%.2f", totalPrice));
        }
    }

    public static void clearCart() {
        cartItems.clear();
    }

    private class CartAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cartItems.size();
        }

        @Override
        public Object getItem(int position) {
            return cartItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(CartActivity.this).inflate(R.layout.item_cart, parent, false);
            }
            Product item = cartItems.get(position);
            TextView tvName = convertView.findViewById(R.id.tvCartProductName);
            TextView tvPrice = convertView.findViewById(R.id.tvCartProductPrice);
            TextView tvCat = convertView.findViewById(R.id.tvCartProductCategory);
            TextView tvQty = convertView.findViewById(R.id.tvCartProductQty);

            tvName.setText(item.name);
            tvPrice.setText("$" + String.format("%.2f", item.price * item.quantity));
            tvCat.setText(item.category);
            tvQty.setText("Qty: " + item.quantity);

            return convertView;
        }
    }
}
