package com.example.sizgro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sizgro.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Spinner spinnerRole;
    private Button btnSignup;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnSignup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);

        // Setup Spinner
        String[] roles = { "user", "seller" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        btnSignup.setOnClickListener(v -> signup());
        tvLogin.setOnClickListener(v -> finish());
    }

    private void signup() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(userId, email, role);

                        // Save user role in Realtime Database
                        FirebaseDatabase.getInstance("https://sizgro-78cab-default-rtdb.firebaseio.com/")
                                .getReference("users")
                                .child(userId)
                                .setValue(user)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        // Save role in SharedPreferences for quick access
                                        saveRoleLocally(role);
                                        navigateToHome(role);
                                    } else {
                                        Toast.makeText(SignupActivity.this,
                                                "DB Error: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                    } else {
                        Toast.makeText(SignupActivity.this, "Auth Failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveRoleLocally(String role) {
        SharedPreferences.Editor editor = getSharedPreferences("SizGroPrefs", MODE_PRIVATE).edit();
        editor.putString("role", role);
        editor.apply();
    }

    private void navigateToHome(String role) {
        Intent intent;
        if ("seller".equals(role)) {
            intent = new Intent(this, SellerHomeActivity.class);
        } else {
            intent = new Intent(this, UserHomeActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
