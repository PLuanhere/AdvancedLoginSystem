package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuestLoadingActivity extends AppCompatActivity {

    private static final String TAG = "GuestLoadingActivity";
    private final int LOADING_TIME = 3000; // 3 seconds loading time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Set the content view first
            setContentView(R.layout.activity_guest_loading);

            Log.d(TAG, "Layout set successfully");

            // Initialize the text view - with null check
            TextView welcomeText = findViewById(R.id.welcomeText);
            if (welcomeText != null) {
                welcomeText.setText("Welcome to GamerPal!");
            } else {
                Log.e(TAG, "welcomeText is null");
            }

            // Use Handler with explicit Looper with error handling
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    Log.d(TAG, "Navigation attempt after loading");

                    // First check if activity is finishing or destroyed
                    if (isFinishing() || isDestroyed()) {
                        Log.d(TAG, "Activity finishing/destroyed, skipping navigation");
                        return;
                    }

                    // Create the intent explicitly
                    Intent intent = new Intent(GuestLoadingActivity.this, GuestOnboardingActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    Log.e(TAG, "Navigation error: " + e.getMessage());
                    e.printStackTrace();

                    try {
                        // Emergency fallback - just go back to MainActivity
                        Intent fallbackIntent = new Intent(GuestLoadingActivity.this, GuestOnboardingActivity.class);
                        startActivity(fallbackIntent);
                        finish();
                    } catch (Exception ex) {
                        Log.e(TAG, "Fallback also failed: " + ex.getMessage());
                        finish(); // Just finish this activity
                    }
                }
            }, LOADING_TIME);

        } catch (Exception e) {
            Log.e(TAG, "onCreate error: " + e.getMessage());
            e.printStackTrace();

            // Critical error recovery
            try {
                Intent intent = new Intent(this, GuestOnboardingActivity.class);
                startActivity(intent);
            } catch (Exception ex) {
                // Nothing more we can do
                Log.e(TAG, "Critical recovery failed");
            } finally {
                finish(); // Make sure to finish this activity
            }
        }
    }

    @Override
    protected void onPause() {
        try {
            super.onPause();
        } catch (Exception e) {
            Log.e(TAG, "onPause error: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            Log.e(TAG, "onDestroy error: " + e.getMessage());
        }
    }
}