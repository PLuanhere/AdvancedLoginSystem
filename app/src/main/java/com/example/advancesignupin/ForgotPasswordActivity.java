package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button sendCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Add text watcher to email input
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable button when there's text
                sendCodeButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        // Set up focus change listener for the email input
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                emailInput.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                emailInput.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });

        // Set up send code button
        sendCodeButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to verify code screen
                Intent intent = new Intent(ForgotPasswordActivity.this, VerifyPasswordCodeActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}