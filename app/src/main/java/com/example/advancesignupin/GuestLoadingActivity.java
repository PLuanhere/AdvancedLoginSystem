package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuestLoadingActivity extends AppCompatActivity {

    private ImageView loadingCircle;
    private TextView welcomeText;
    private final int LOADING_TIME = 3000; // 3 seconds of loading time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_loading);

        // Initialize views
        loadingCircle = findViewById(R.id.loadingCircle);
        welcomeText = findViewById(R.id.welcomeText);

        // Set welcome text
        welcomeText.setText("Welcome to GamerPal!");

        // Apply rotation animation to the loading circle
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        loadingCircle.startAnimation(rotateAnimation);

        // Delay to simulate loading
        new Handler().postDelayed(() -> {
            // Navigate to the single onboarding activity
            Intent intent = new Intent(GuestLoadingActivity.this, GuestOnboardingActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        }, LOADING_TIME);
    }
}