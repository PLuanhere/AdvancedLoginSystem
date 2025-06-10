package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText displayNameInput;
    private TextView displayNameCounter;
    private EditText usernameInput;
    private TextView usernameCounter;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private ImageView passwordToggle;
    private ImageView confirmPasswordToggle;
    private Button continueButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        // Initialize views@
        emailInput = findViewById(R.id.emailInput);
        displayNameInput = findViewById(R.id.displayNameInput);
        displayNameCounter = findViewById(R.id.displayNameCounter);
        usernameInput = findViewById(R.id.usernameInput);
        usernameCounter = findViewById(R.id.usernameCounter);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        passwordToggle = findViewById(R.id.passwordToggle);
        confirmPasswordToggle = findViewById(R.id.confirmPasswordToggle);
        continueButton = findViewById(R.id.continueButton);
        loginLink = findViewById(R.id.loginLink);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up character counters
        setupTextCounter(displayNameInput, displayNameCounter, 20);
        setupTextCounter(usernameInput, usernameCounter, 20);

        // Set up focus change listeners for all input fields
        setupFocusChangeListener(emailInput);
        setupFocusChangeListener(displayNameInput);
        setupFocusChangeListener(usernameInput);
        setupFocusChangeListener(passwordInput);
        setupFocusChangeListener(confirmPasswordInput);

        // Set up password visibility toggles
        passwordToggle.setOnClickListener(v -> togglePasswordVisibility(passwordInput, passwordToggle));
        confirmPasswordToggle.setOnClickListener(v -> togglePasswordVisibility(confirmPasswordInput, confirmPasswordToggle));

        continueButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Navigate to verification screen
                Intent intent = new Intent(SignUpActivity.this, SignUpVerificationActivity.class);
                intent.putExtra("EMAIL", emailInput.getText().toString().trim());
                startActivity(intent);
            }
        });

        // Set up login link
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupFocusChangeListener(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // When focused, use purple outlined background
                editText.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                // When not focused, use regular input background
                editText.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordField, ImageView toggleIcon) {
        if (passwordField.getTransformationMethod() instanceof PasswordTransformationMethod) {
            passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            toggleIcon.setImageResource(R.drawable.ic_visibility_off);
        } else {
            passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleIcon.setImageResource(R.drawable.ic_visibility);
        }
        // Place cursor at the end of the text
        passwordField.setSelection(passwordField.getText().length());
    }

    private void setupTextCounter(EditText editText, TextView counterView, int maxLength) {
        counterView.setText("0/" + maxLength);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterView.setText(s.length() + "/" + maxLength);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > maxLength) {
                    editText.setText(s.subSequence(0, maxLength));
                    editText.setSelection(maxLength);
                }
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate email
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            isValid = false;
        } else {
            emailInput.setError(null);
        }

        // Validate display name
        String displayName = displayNameInput.getText().toString().trim();
        if (displayName.isEmpty()) {
            displayNameInput.setError("Display name is required");
            isValid = false;
        }

        // Validate username
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInput.setError("Username is required");
            isValid = false;
        }

        // Validate password
        String password = passwordInput.getText().toString();
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            passwordInput.setError("Password must be at least 8 characters");
            isValid = false;
        } else if (!password.matches(".*[!@#$%&*].*")) {
            passwordInput.setError("Password must contain at least 1 special character");
            isValid = false;
        } else if (!password.matches(".*\\d.*")) {
            passwordInput.setError("Password must contain at least 1 number");
            isValid = false;
        }

        // Validate confirm password
        String confirmPassword = confirmPasswordInput.getText().toString();
        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.setError("Confirm your password");
            isValid = false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordInput.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }
}