package com.example.sizgro; // Ye line code execution ka ek basic step perform kar rahi hai.

import android.content.Intent; // Ye syntax system ki base execution me madad kar raha hai.
import android.content.SharedPreferences; // Ye syntax system ki base execution me madad kar raha hai.
import android.os.Bundle; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.os.Handler; // Ye syntax system ki base execution me madad kar raha hai.
import android.view.View; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

import androidx.appcompat.app.AppCompatActivity; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

import com.google.firebase.auth.FirebaseAuth; // Yahan par data initialization ya function call ho raha hai.
import com.google.firebase.auth.FirebaseUser; // Program ka control flow yahan par practically manage kiya ja raha hai.

public class launcher extends AppCompatActivity { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

    @Override // Execution environment ke standard rules ko follow karte hue line build hui hai.
    protected void onCreate(Bundle savedInstanceState) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
        super.onCreate(savedInstanceState); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        setContentView(R.layout.activity_launcher); // Ye syntax system ki base execution me madad kar raha hai.

        new Handler().postDelayed(() -> { // Program ka control flow yahan par practically manage kiya ja raha hai.
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            if (currentUser != null) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                // Check role from SharedPreferences
                SharedPreferences prefs = getSharedPreferences("SizGroPrefs", MODE_PRIVATE); // Ye line code execution ka ek basic step perform kar rahi hai.
                String role = prefs.getString("role", "user"); // Program ka control flow yahan par practically manage kiya ja raha hai.

                if ("seller".equals(role)) { // Ye syntax system ki base execution me madad kar raha hai.
                    startActivity(new Intent(launcher.this, SellerHomeActivity.class)); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                } else { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    startActivity(new Intent(launcher.this, UserHomeActivity.class)); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
            } else { // Yahan par data initialization ya function call ho raha hai.
                startActivity(new Intent(launcher.this, LoginActivity.class)); // Ye line code execution ka ek basic step perform kar rahi hai.
            } // Program ka control flow yahan par practically manage kiya ja raha hai.
            finish(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        }, 2000); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
} // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.