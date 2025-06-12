package com.example.advancesignupin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private TextView continueAsGuestText;
    private LinearLayout googleSignUpContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);
        continueAsGuestText = findViewById(R.id.continueAsGuestText);
        googleSignUpContainer = findViewById(R.id.googleSignUpContainer);

        // Set up sign up button
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Set up login button
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Set up continue as guest option
        continueAsGuestText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GuestLoadingActivity.class);
            startActivity(intent);
        });

        // Set up Google sign up
        googleSignUpContainer.setOnClickListener(v -> {
            showGoogleSignInDialog();
        });

    }

    private void showGoogleSignInDialog() {
        // Create the dialog
        Dialog googleSignInDialog = new Dialog(this);
        googleSignInDialog.setContentView(R.layout.dialog_google_signin);
        googleSignInDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Find views in dialog
        Button continueButton = googleSignInDialog.findViewById(R.id.continueButton);

        // Set up continue button
        continueButton.setOnClickListener(v -> {
            googleSignInDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, SignUpSocialActivity.class);
            startActivity(intent);
        });

        // Show the dialog
        googleSignInDialog.show();
    }
}


