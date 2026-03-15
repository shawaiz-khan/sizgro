package com.example.sizgro; // Ye syntax system ki base execution me madad kar raha hai.

import android.os.Bundle; // Execution environment ke standard rules ko follow karte hue line build hui hai.

import androidx.activity.EdgeToEdge; // Yahan par data initialization ya function call ho raha hai.
import androidx.appcompat.app.AppCompatActivity; // Yahan par data initialization ya function call ho raha hai.
import androidx.core.graphics.Insets; // Program ka control flow yahan par practically manage kiya ja raha hai.
import androidx.core.view.ViewCompat; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import androidx.core.view.WindowInsetsCompat; // In variables aur methods se framework ka internal object state maintain ho raha hai.

public class MainActivity extends AppCompatActivity { // Application codebase ka ek zaroori hissa run ho raha hai is block se.

    @Override // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    protected void onCreate(Bundle savedInstanceState) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        super.onCreate(savedInstanceState); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        EdgeToEdge.enable(this); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        setContentView(R.layout.activity_main); // Program ka control flow yahan par practically manage kiya ja raha hai.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Ye syntax system ki base execution me madad kar raha hai.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Ye line code execution ka ek basic step perform kar rahi hai.
            return insets; // Ye line code execution ka ek basic step perform kar rahi hai.
        }); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
} // Program ka control flow yahan par practically manage kiya ja raha hai.