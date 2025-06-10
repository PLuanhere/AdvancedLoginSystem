package com.example.advancesignupin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Regular sign up button
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Login button
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Find the Google sign up section
        LinearLayout googleSignUpContainer = findViewById(R.id.googleSignUpContainer);
        googleSignUpContainer.setOnClickListener(v -> showGoogleSignInDialog());

        // Continue as guest
        TextView guestText = findViewById(R.id.guestText);
        guestText.setOnClickListener(v -> {
            // Handle guest login
            Toast.makeText(MainActivity.this, "Continuing as guest", Toast.LENGTH_SHORT).show();
        });
    }

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
            // Navigate to social sign up screen
            Intent intent = new Intent(MainActivity.this, SignUpSocialActivity.class);
            intent.putExtra("EMAIL", "sampleemail@gmail.com");
            startActivity(intent);
        });

        dialog.show();
    }
}