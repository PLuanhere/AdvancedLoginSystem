package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpSocialActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputLayout usernameInputLayout;
    private TextView emailDisplay;
    private MaterialButton continueButton;
    private TextView termsOfServiceLink;
    private TextView privacyPolicyLink;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_social);

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        emailDisplay = findViewById(R.id.emailDisplay);
        continueButton = findViewById(R.id.continueButton);
        termsOfServiceLink = findViewById(R.id.termsOfServiceLink);
        privacyPolicyLink = findViewById(R.id.privacyPolicyLink);
        loginLink = findViewById(R.id.loginLink);
        ImageButton backButton = findViewById(R.id.backButton);

        // Get email from Intent
        String email = getIntent().getStringExtra("EMAIL");
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
                String emailText = emailDisplay.getText().toString().trim();

                // Navigate to verification screen
                Intent intent = new Intent(SignUpSocialActivity.this, SignUpVerificationActivity.class);
                intent.putExtra("EMAIL", emailText);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
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
}