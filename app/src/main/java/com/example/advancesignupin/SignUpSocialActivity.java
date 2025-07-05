package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpSocialActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputLayout usernameInputLayout;
    private TextView emailDisplay;
    private MaterialButton continueButton;
    private TextView termsOfServiceLink;
    private TextView privacyPolicyLink;
    private TextView loginLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_social);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        emailDisplay = findViewById(R.id.emailDisplay);
        continueButton = findViewById(R.id.continueButton);
        termsOfServiceLink = findViewById(R.id.termsOfServiceLink);
        privacyPolicyLink = findViewById(R.id.privacyPolicyLink);
        loginLink = findViewById(R.id.loginLink);
        ImageButton backButton = findViewById(R.id.backButton);

        // Get email from Intent or FirebaseUser
        FirebaseUser user = mAuth.getCurrentUser();
        final String email =
                (getIntent().getStringExtra("EMAIL") != null && !getIntent().getStringExtra("EMAIL").isEmpty())
                        ? getIntent().getStringExtra("EMAIL")
                        : (user != null && user.getEmail() != null ? user.getEmail() : null);
        if (email != null && !email.isEmpty()) {
            emailDisplay.setText(email);
        }

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up continue button
        continueButton.setOnClickListener(v -> {
            // Validate username and proceed to verification screen
            if (validateUsername()) {
                String username = usernameInput.getText().toString().trim();
                updateDisplayNameAndContinue(username, email);
            }
        });

        // Set up links
        termsOfServiceLink.setOnClickListener(v -> {
            Toast.makeText(SignUpSocialActivity.this, "Terms of Service", Toast.LENGTH_SHORT).show();
            // Open Terms of Service
        });

        privacyPolicyLink.setOnClickListener(v -> {
            Toast.makeText(SignUpSocialActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
            // Open Privacy Policy
        });

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpSocialActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateUsername() {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInputLayout.setError("Username is required");
            return false;
        } else if (username.length() < 3) {
            usernameInputLayout.setError("Username must be at least 3 characters");
            return false;
        } else {
            usernameInputLayout.setError(null);
            return true;
        }
    }

    private void updateDisplayNameAndContinue(String username, String email) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Lưu username vào displayName
        user.updateProfile(new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Sign up success!", Toast.LENGTH_SHORT).show();
                        // Chuyển sang màn hình xác minh hoặc màn hình chính
                        Intent intent = new Intent(this, SignUpVerificationActivity.class);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Update username failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}