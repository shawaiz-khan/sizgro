package com.example.sizgro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Check role from SharedPreferences
                SharedPreferences prefs = getSharedPreferences("SizGroPrefs", MODE_PRIVATE);
                String role = prefs.getString("role", "user");

                if ("seller".equals(role)) {
                    startActivity(new Intent(launcher.this, SellerHomeActivity.class));
                } else {
                    startActivity(new Intent(launcher.this, UserHomeActivity.class));
                }
            } else {
                startActivity(new Intent(launcher.this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}