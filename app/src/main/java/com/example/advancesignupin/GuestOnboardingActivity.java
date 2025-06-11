package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class GuestOnboardingActivity extends AppCompatActivity {

    private static final String TAG = "GuestOnboardingActivity";
    private int currentPage = 1;
    private final int TOTAL_PAGES = 3;

    private View indicator1, indicator2, indicator3;
    private TextView featureTitle;
    private MaterialButton nextButton;
    private TextView skipIntroText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_onboarding);

        Log.d(TAG, "GuestOnboardingActivity created");

        // Initialize views
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        featureTitle = findViewById(R.id.featureTitle);
        nextButton = findViewById(R.id.nextButton);
        skipIntroText = findViewById(R.id.skipIntroText);

        // Set initial page content
        updatePageContent();

        // Set up next button
        nextButton.setOnClickListener(v -> {
            if (currentPage < TOTAL_PAGES) {
                currentPage++;
                updatePageContent();
            } else {
                // On last page, navigate to success screen
                navigateToSuccessScreen();
            }
        });

        // Set up skip intro
        skipIntroText.setOnClickListener(v -> {
            navigateToSuccessScreen();
        });
    }

    private void updatePageContent() {
        // Update feature title based on current page
        String[] titles = {"Feature 1", "Feature 2", "Feature 3"};
        featureTitle.setText(titles[currentPage - 1]);

        // Update indicators
        updateIndicators();

        // Update button text on last page
        if (currentPage == TOTAL_PAGES) {
            nextButton.setText("Get Started");
        } else {
            nextButton.setText("Next");
        }
    }

    private void updateIndicators() {
        // Reset all indicators
        indicator1.setBackgroundResource(R.drawable.indicator_inactive);
        indicator2.setBackgroundResource(R.drawable.indicator_inactive);
        indicator3.setBackgroundResource(R.drawable.indicator_inactive);

        // Set active indicators based on current page
        switch (currentPage) {
            case 1:
                indicator1.setBackgroundResource(R.drawable.indicator_active);
                break;
            case 2:
                indicator1.setBackgroundResource(R.drawable.indicator_active);
                indicator2.setBackgroundResource(R.drawable.indicator_active);
                break;
            case 3:
                indicator1.setBackgroundResource(R.drawable.indicator_active);
                indicator2.setBackgroundResource(R.drawable.indicator_active);
                indicator3.setBackgroundResource(R.drawable.indicator_active);
                break;
        }
    }

    private void navigateToSuccessScreen() {
        Intent intent = new Intent(GuestOnboardingActivity.this, SignUpSuccessActivity.class);
        startActivity(intent);
        finish();
    }
}