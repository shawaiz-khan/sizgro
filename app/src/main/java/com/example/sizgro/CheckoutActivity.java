    package com.example.sizgro; // In variables aur methods se framework ka internal object state maintain ho raha hai.

    import android.os.Bundle; // Execution environment ke standard rules ko follow karte hue line build hui hai.
    import android.widget.ArrayAdapter; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    import android.widget.Button; // Ye line code execution ka ek basic step perform kar rahi hai.
    import android.widget.EditText; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    import android.widget.Spinner; // Program ka control flow yahan par practically manage kiya ja raha hai.
    import android.widget.TextView; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    import android.widget.Toast; // In variables aur methods se framework ka internal object state maintain ho raha hai.

    import androidx.appcompat.app.AlertDialog; // Execution environment ke standard rules ko follow karte hue line build hui hai.
    import androidx.appcompat.app.AppCompatActivity; // In variables aur methods se framework ka internal object state maintain ho raha hai.

    public class CheckoutActivity extends AppCompatActivity { // Yahan par data initialization ya function call ho raha hai.

        private EditText etAddress; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        private Spinner spinnerPayment; // Execution environment ke standard rules ko follow karte hue line build hui hai.
        private Button btnPlaceOrder; // Ye line code execution ka ek basic step perform kar rahi hai.
        private TextView tvSummarySubtotal, tvSummaryTotal, btnBack; // Program ka control flow yahan par practically manage kiya ja raha hai.

        @Override // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        protected void onCreate(Bundle savedInstanceState) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            super.onCreate(savedInstanceState); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            setContentView(R.layout.activity_checkout); // Ye line code execution ka ek basic step perform kar rahi hai.

            etAddress = findViewById(R.id.etAddress); // Yahan par data initialization ya function call ho raha hai.
            spinnerPayment = findViewById(R.id.spinnerPayment); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            btnPlaceOrder = findViewById(R.id.btnPlaceOrder); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            tvSummarySubtotal = findViewById(R.id.tvSummarySubtotal); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            tvSummaryTotal = findViewById(R.id.tvSummaryTotal); // Yahan par data initialization ya function call ho raha hai.
            btnBack = findViewById(R.id.btnBack); // Ye syntax system ki base execution me madad kar raha hai.

            // Setup Payment Spinner
            String[] payments = { "Cash on Delivery", "Credit Card", "Mobile Wallet" }; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payments); // Ye syntax system ki base execution me madad kar raha hai.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            spinnerPayment.setAdapter(adapter); // In variables aur methods se framework ka internal object state maintain ho raha hai.

            // Get total and items from intent
            double subtotal = getIntent().getDoubleExtra("totalPrice", 0.0); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            String itemsSummary = getIntent().getStringExtra("itemsSummary"); // Ye syntax system ki base execution me madad kar raha hai.
            if (itemsSummary == null) // Execution environment ke standard rules ko follow karte hue line build hui hai.
                itemsSummary = "Items in cart"; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

            tvSummarySubtotal.setText("$" + String.format("%.2f", subtotal)); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            tvSummaryTotal.setText("$" + String.format("%.2f", subtotal + 2.0)); // Adding fictional delivery fee // Yahan par data initialization ya function call ho raha hai.
            ((TextView) findViewById(R.id.tvCheckoutItems)).setText(itemsSummary); // Ye line code execution ka ek basic step perform kar rahi hai.

            btnBack.setOnClickListener(v -> finish()); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

            btnPlaceOrder.setOnClickListener(v -> { // Yahan par data initialization ya function call ho raha hai.
                String address = etAddress.getText().toString().trim(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                if (address.isEmpty()) { // Yahan par data initialization ya function call ho raha hai.
                    Toast.makeText(this, "Please enter a delivery address", Toast.LENGTH_SHORT).show(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                    return; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                } // Execution environment ke standard rules ko follow karte hue line build hui hai.

                // Mock success dialog
                new AlertDialog.Builder(this) // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        .setTitle("Order Confirmed") // Yahan par data initialization ya function call ho raha hai.
                        .setMessage("Your order has been placed successfully!") // Yahan par data initialization ya function call ho raha hai.
                        .setCancelable(false) // Ye syntax system ki base execution me madad kar raha hai.
                        .setPositiveButton("Back to Home", (dialog, which) -> { // Ye line code execution ka ek basic step perform kar rahi hai.
                            // Clear fields, static cart and go back to Home
                            CartActivity.clearCart(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                            etAddress.setText(""); // Ye syntax system ki base execution me madad kar raha hai.
                            finishAffinity(); // Just a simple way for assignment to clear stack // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                            startActivity(new android.content.Intent(this, UserHomeActivity.class)); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                        }) // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        .show(); // Yahan par data initialization ya function call ho raha hai.
            }); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
    } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
