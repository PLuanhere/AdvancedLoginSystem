package com.example.advancesignupin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private CheckBox rememberMeCheckbox;
    private MaterialButton loginButton;
    private TextView forgotPasswordLink;
    private LinearLayout googleLoginContainer;
    private TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        googleLoginContainer = findViewById(R.id.googleLoginContainer);
        signUpLink = findViewById(R.id.signUpLink);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up login button
        loginButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Perform login
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up Google login - reuse the dialog from sign up
        googleLoginContainer.setOnClickListener(v -> showGoogleSignInDialog());

        // Set up forgot password
        forgotPasswordLink.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            // Navigate to forgot password screen
        });

        // Set up sign up link
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPasswordLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInputLayout.setError("Username is required");
            isValid = false;
        } else {
            usernameInputLayout.setError(null);
        }

        String password = passwordInput.getText().toString().trim();
        if (password.isEmpty()) {
            passwordInputLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            isValid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        return isValid;
    }

    // Reuse the existing Google sign-in dialog
    private void showGoogleSignInDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_google_signin);
        dialog.setCancelable(true);

        // Make dialog background transparent
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get the continue button
        Button continueButton = dialog.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> {
            dialog.dismiss();
            // Navigate to social login success screen
            Intent intent = new Intent(LoginActivity.this, SignUpSocialActivity.class);
            intent.putExtra("EMAIL", "sampleemail@gmail.com");
            startActivity(intent);
        });

        dialog.show();
    }
}