package com.example.sizgro; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

import android.content.Intent; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import android.content.SharedPreferences; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.os.Bundle; // Yahan par data initialization ya function call ho raha hai.
import android.widget.Button; // Ye line code execution ka ek basic step perform kar rahi hai.
import android.widget.EditText; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
import android.widget.TextView; // Program ka control flow yahan par practically manage kiya ja raha hai.
import android.widget.Toast; // Execution environment ke standard rules ko follow karte hue line build hui hai.

import androidx.appcompat.app.AppCompatActivity; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

import com.example.sizgro.models.User; // Ye line code execution ka ek basic step perform kar rahi hai.
import com.google.firebase.auth.FirebaseAuth; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
import com.google.firebase.database.DataSnapshot; // Ye syntax system ki base execution me madad kar raha hai.
import com.google.firebase.database.DatabaseError; // Ye syntax system ki base execution me madad kar raha hai.
import com.google.firebase.database.FirebaseDatabase; // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
import com.google.firebase.database.ValueEventListener; // Application codebase ka ek zaroori hissa run ho raha hai is block se.

public class LoginActivity extends AppCompatActivity { // Program ka control flow yahan par practically manage kiya ja raha hai.

    private EditText etEmail, etPassword; // Execution environment ke standard rules ko follow karte hue line build hui hai.
    private Button btnLogin; // Application codebase ka ek zaroori hissa run ho raha hai is block se.
    private TextView tvSignup; // Program ka control flow yahan par practically manage kiya ja raha hai.
    private FirebaseAuth mAuth; // Ye syntax system ki base execution me madad kar raha hai.

    @Override // Yahan par data initialization ya function call ho raha hai.
    protected void onCreate(Bundle savedInstanceState) { // Program ka control flow yahan par practically manage kiya ja raha hai.
        super.onCreate(savedInstanceState); // Yahan par data initialization ya function call ho raha hai.
        setContentView(R.layout.activity_login); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

        mAuth = FirebaseAuth.getInstance(); // Yahan par data initialization ya function call ho raha hai.

        etEmail = findViewById(R.id.etEmail); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
        etPassword = findViewById(R.id.etPassword); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        btnLogin = findViewById(R.id.btnLogin); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        tvSignup = findViewById(R.id.tvSignup); // In variables aur methods se framework ka internal object state maintain ho raha hai.

        btnLogin.setOnClickListener(v -> login()); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
        tvSignup.setOnClickListener(v -> { // Yahan par data initialization ya function call ho raha hai.
            startActivity(new Intent(LoginActivity.this, SignupActivity.class)); // Ye line code execution ka ek basic step perform kar rahi hai.
        }); // Program ka control flow yahan par practically manage kiya ja raha hai.
    } // Ye line code execution ka ek basic step perform kar rahi hai.

    private void login() { // Yahan par data initialization ya function call ho raha hai.
        String email = etEmail.getText().toString().trim(); // Yahan par data initialization ya function call ho raha hai.
        String password = etPassword.getText().toString().trim(); // Ye line code execution ka ek basic step perform kar rahi hai.

        if (email.isEmpty() || password.isEmpty()) { // Yahan par data initialization ya function call ho raha hai.
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show(); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            return; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        } // Ye syntax system ki base execution me madad kar raha hai.

        mAuth.signInWithEmailAndPassword(email, password) // Ye syntax system ki base execution me madad kar raha hai.
                .addOnCompleteListener(this, task -> { // Ye syntax system ki base execution me madad kar raha hai.
                    if (task.isSuccessful()) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        fetchUserRole(mAuth.getCurrentUser().getUid()); // Ye line code execution ka ek basic step perform kar rahi hai.
                    } else { // Program ka control flow yahan par practically manage kiya ja raha hai.
                        Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), // In variables aur methods se framework ka internal object state maintain ho raha hai.
                                Toast.LENGTH_SHORT).show(); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                    } // Program ka control flow yahan par practically manage kiya ja raha hai.
                }); // Yahan par data initialization ya function call ho raha hai.
    } // In variables aur methods se framework ka internal object state maintain ho raha hai.

    private void fetchUserRole(String uid) { // Program ka control flow yahan par practically manage kiya ja raha hai.
        FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/").getReference("users") // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                .child(uid) // Execution environment ke standard rules ko follow karte hue line build hui hai.
                .addListenerForSingleValueEvent(new ValueEventListener() { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                    @Override // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                    public void onDataChange(DataSnapshot snapshot) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        User user = snapshot.getValue(User.class); // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                        if (user != null) { // Yahan par data initialization ya function call ho raha hai.
                            saveRoleLocally(user.role); // Execution environment ke standard rules ko follow karte hue line build hui hai.
                            navigateToHome(user.role); // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
                        } else { // In variables aur methods se framework ka internal object state maintain ho raha hai.
                            // Fallback if role is not found
                            navigateToHome("user"); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
                        } // Application codebase ka ek zaroori hissa run ho raha hai is block se.
                    } // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.

                    @Override // Ye syntax system ki base execution me madad kar raha hai.
                    public void onCancelled(DatabaseError error) { // Ye line code execution ka ek basic step perform kar rahi hai.
                        Toast.makeText(LoginActivity.this, "Error fetching role", Toast.LENGTH_SHORT).show(); // Program ka control flow yahan par practically manage kiya ja raha hai.
                    } // Yahan par data initialization ya function call ho raha hai.
                }); // Ye syntax system ki base execution me madad kar raha hai.
    } // Program ka control flow yahan par practically manage kiya ja raha hai.

    private void saveRoleLocally(String role) { // Ye line code execution ka ek basic step perform kar rahi hai.
        SharedPreferences.Editor editor = getSharedPreferences("SizGroPrefs", MODE_PRIVATE).edit(); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        editor.putString("role", role); // In variables aur methods se framework ka internal object state maintain ho raha hai.
        editor.apply(); // Ye syntax system ki base execution me madad kar raha hai.
    } // Program ka control flow yahan par practically manage kiya ja raha hai.

    private void navigateToHome(String role) { // Execution environment ke standard rules ko follow karte hue line build hui hai.
        Intent intent; // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        if ("seller".equals(role)) { // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
            intent = new Intent(this, SellerHomeActivity.class); // Program ka control flow yahan par practically manage kiya ja raha hai.
        } else { // In variables aur methods se framework ka internal object state maintain ho raha hai.
            intent = new Intent(this, UserHomeActivity.class); // Ye syntax system ki base execution me madad kar raha hai.
        } // Ye syntax system ki base execution me madad kar raha hai.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Is line ka main maqsad component structure ya memory ko effectively utilize karna lagta hai.
        startActivity(intent); // Execution environment ke standard rules ko follow karte hue line build hui hai.
        finish(); // Ye syntax system ki base execution me madad kar raha hai.
    } // In variables aur methods se framework ka internal object state maintain ho raha hai.
} // Dependency structure ya object reference ki default configuration chal rahi hai yahan.
