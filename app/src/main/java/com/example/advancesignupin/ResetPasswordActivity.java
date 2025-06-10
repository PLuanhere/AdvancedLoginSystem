package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button resetPasswordButton;
    private String userEmail;

    // Regular expression to validate password
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Get email from previous activity
        userEmail = getIntent().getStringExtra("EMAIL");

        // Initialize views
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up focus change listener for password inputs
        passwordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordInput.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                passwordInput.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });

        confirmPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                confirmPasswordInput.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                confirmPasswordInput.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });

        // Add text watchers to enable/disable the reset button based on input
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPasswordsAndEnableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        };

        passwordInput.addTextChangedListener(textWatcher);
        confirmPasswordInput.addTextChangedListener(textWatcher);

        // Set up reset password button
        resetPasswordButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Simulate password reset success
                Toast.makeText(ResetPasswordActivity.this, "Password reset successful!", Toast.LENGTH_SHORT).show();

                // Navigate back to login screen
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateInputs() {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pattern.matcher(password).matches()) {
            Toast.makeText(this, "Password does not meet requirements", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void checkPasswordsAndEnableButton() {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        boolean isValid = !password.isEmpty() && !confirmPassword.isEmpty() &&
                password.equals(confirmPassword) && pattern.matcher(password).matches();
        resetPasswordButton.setEnabled(isValid);
    }
}