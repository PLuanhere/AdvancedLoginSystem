package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private TextView continueAsGuestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);
        continueAsGuestText = findViewById(R.id.continueAsGuestText);

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
    }
}