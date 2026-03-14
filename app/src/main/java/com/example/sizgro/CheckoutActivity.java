    package com.example.sizgro;

    import android.os.Bundle;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    public class CheckoutActivity extends AppCompatActivity {

        private EditText etAddress;
        private Spinner spinnerPayment;
        private Button btnPlaceOrder;
        private TextView tvSummarySubtotal, tvSummaryTotal, btnBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_checkout);

            etAddress = findViewById(R.id.etAddress);
            spinnerPayment = findViewById(R.id.spinnerPayment);
            btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
            tvSummarySubtotal = findViewById(R.id.tvSummarySubtotal);
            tvSummaryTotal = findViewById(R.id.tvSummaryTotal);
            btnBack = findViewById(R.id.btnBack);

            // Setup Payment Spinner
            String[] payments = { "Cash on Delivery", "Credit Card", "Mobile Wallet" };
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payments);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPayment.setAdapter(adapter);

            // Get total and items from intent
            double subtotal = getIntent().getDoubleExtra("totalPrice", 0.0);
            String itemsSummary = getIntent().getStringExtra("itemsSummary");
            if (itemsSummary == null)
                itemsSummary = "Items in cart";

            tvSummarySubtotal.setText("$" + String.format("%.2f", subtotal));
            tvSummaryTotal.setText("$" + String.format("%.2f", subtotal + 2.0)); // Adding fictional delivery fee
            ((TextView) findViewById(R.id.tvCheckoutItems)).setText(itemsSummary);

            btnBack.setOnClickListener(v -> finish());

            btnPlaceOrder.setOnClickListener(v -> {
                String address = etAddress.getText().toString().trim();
                if (address.isEmpty()) {
                    Toast.makeText(this, "Please enter a delivery address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mock success dialog
                new AlertDialog.Builder(this)
                        .setTitle("Order Confirmed")
                        .setMessage("Your order has been placed successfully!")
                        .setCancelable(false)
                        .setPositiveButton("Back to Home", (dialog, which) -> {
                            // Clear fields, static cart and go back to Home
                            CartActivity.clearCart();
                            etAddress.setText("");
                            finishAffinity(); // Just a simple way for assignment to clear stack
                            startActivity(new android.content.Intent(this, UserHomeActivity.class));
                        })
                        .show();
            });
        }
    }
