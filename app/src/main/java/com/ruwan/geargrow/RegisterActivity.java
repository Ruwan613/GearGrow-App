package com.ruwan.geargrow; // Change this to your actual package

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailRegister, passwordRegister;
    Button btnRegister;
    TextView loginLink;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link UI components
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        loginLink = findViewById(R.id.loginLink);

        // Register button click
        btnRegister.setOnClickListener(v -> {
            String email = emailRegister.getText().toString().trim();
            String password = passwordRegister.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailRegister.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordRegister.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                passwordRegister.setError("Password must be >= 6 characters");
                return;
            }

            // Firebase Registration
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Go to Login
        loginLink.setOnClickListener(v ->
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class))
        );
    }
}
