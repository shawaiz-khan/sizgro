package com.example.sizgro; // Ye syntax system ki base execution me madad kar raha hai.

import android.content.Intent; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.content.SharedPreferences; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.os.Bundle; // Yahan par data initialization ya function call ho raha hai.
import android.view.View; // Ye syntax system ki base execution me madad kar raha hai.
import android.widget.ArrayAdapter; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import android.widget.Button; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import android.widget.EditText; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.widget.Spinner; // Execution environment ke standard rules ko follow karte hue line build hui hai.
import android.widget.TextView; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import android.widget.Toast; // In variables aur methods se framework ka internal object state maintain ho raha hai.

import androidx.appcompat.app.AppCompatActivity; // Ye line code execution ka ek basic step perform kar rahi hai.

import com.example.sizgro.models.User; // In variables aur methods se framework ka internal object state maintain ho raha hai.
import com.google.firebase.auth.FirebaseAuth; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import com.google.firebase.database.FirebaseDatabase; // Program ka control flow yahan par practically manage kiya ja raha hai.

public class SignupActivity extends AppCompatActivity { // Yahan par data initialization ya function call ho raha hai.

    private EditText etEmail, etPassword; // In variables aur methods se framework ka internal object state maintain ho raha hai.
    private Spinner spinnerRole; // Program ka control flow yahan par practically manage kiya ja raha hai.
    private Button btnSignup; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    private TextView tvLogin; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
    private FirebaseAuth mAuth; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

    @Override // Yahan par data initialization ya function call ho raha hai.
    protected void onCreate(Bundle savedInstanceState) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
        super.onCreate(savedInstanceState); // Ye line code execution ka ek basic step perform kar rahi hai.
        setContentView(R.layout.activity_signup); // Yahan par data initialization ya function call ho raha hai.

        mAuth = FirebaseAuth.getInstance(); // Yahan par data initialization ya function call ho raha hai.

        etEmail = findViewById(R.id.etEmail); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        etPassword = findViewById(R.id.etPassword); // Program ka control flow yahan par practically manage kiya ja raha hai.
        spinnerRole = findViewById(R.id.spinnerRole); // Ye line code execution ka ek basic step perform kar rahi hai.
        btnSignup = findViewById(R.id.btnSignup); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        tvLogin = findViewById(R.id.tvLogin); // Ye syntax system ki base execution me madad kar raha hai.

        // Setup Spinner
        String[] roles = { "user", "seller" }; // Ye line code execution ka ek basic step perform kar rahi hai.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        spinnerRole.setAdapter(adapter); // Program ka control flow yahan par practically manage kiya ja raha hai.

        btnSignup.setOnClickListener(v -> signup()); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        tvLogin.setOnClickListener(v -> finish()); // Execution environment ke standard rules ko follow karte hue line build hui hai.
    } // Dependency structure ya object reference ki default configuration chal rahi hai yahan.

    private void signup() { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        String email = etEmail.getText().toString().trim(); // Yahan par data initialization ya function call ho raha hai.
        String password = etPassword.getText().toString().trim(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        String role = spinnerRole.getSelectedItem().toString(); // Execution environment ke standard rules ko follow karte hue line build hui hai.

        if (email.isEmpty() || password.isEmpty()) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
            return; // Ye line code execution ka ek basic step perform kar rahi hai.
        } // Execution environment ke standard rules ko follow karte hue line build hui hai.

        mAuth.createUserWithEmailAndPassword(email, password) // Program ka control flow yahan par practically manage kiya ja raha hai.
                .addOnCompleteListener(this, task -> { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                    if (task.isSuccessful()) { // Program ka control flow yahan par practically manage kiya ja raha hai.
                        String userId = mAuth.getCurrentUser().getUid(); // Yahan par data initialization ya function call ho raha hai.
                        User user = new User(userId, email, role); // Ye line code execution ka ek basic step perform kar rahi hai.

                        // Save user role in Realtime Database
                        FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/") // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                .getReference("users") // Ye syntax system ki base execution me madad kar raha hai.
                                .child(userId) // Yahan par data initialization ya function call ho raha hai.
                                .setValue(user) // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                                .addOnCompleteListener(dbTask -> { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                    if (dbTask.isSuccessful()) { // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                                        // Save role in SharedPreferences for quick access
                                        saveRoleLocally(role); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                        navigateToHome(role); // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                    } else { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                                        Toast.makeText(SignupActivity.this, // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                                "DB Error: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT) // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                                .show(); // Program ka control flow yahan par practically manage kiya ja raha hai.
                                    } // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                }); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                    } else { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        Toast.makeText(SignupActivity.this, "Auth Failed: " + task.getException().getMessage(), // Execution environment ke standard rules ko follow karte hue line build hui hai.
                                Toast.LENGTH_SHORT).show(); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                }); // Ye line code execution ka ek basic step perform kar rahi hai.
    } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

    private void saveRoleLocally(String role) { // In variables aur methods se framework ka internal object state maintain ho raha hai.
        SharedPreferences.Editor editor = getSharedPreferences("SizGroPrefs", MODE_PRIVATE).edit(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        editor.putString("role", role); // Program ka control flow yahan par practically manage kiya ja raha hai.
        editor.apply(); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    } // Application codebase ka ek zaroori hissa run ho raha hai is block se.

    private void navigateToHome(String role) { // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        Intent intent; // In variables aur methods se framework ka internal object state maintain ho raha hai.
        if ("seller".equals(role)) { // Yahan par data initialization ya function call ho raha hai.
            intent = new Intent(this, SellerHomeActivity.class); // Ye line code execution ka ek basic step perform kar rahi hai.
        } else { // Execution environment ke standard rules ko follow karte hue line build hui hai.
            intent = new Intent(this, UserHomeActivity.class); // Ye line code execution ka ek basic step perform kar rahi hai.
        } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Yahan par data initialization ya function call ho raha hai.
        startActivity(intent); // Ye syntax system ki base execution me madad kar raha hai.
    } // In variables aur methods se framework ka internal object state maintain ho raha hai.
} // Yahan par data initialization ya function call ho raha hai.
