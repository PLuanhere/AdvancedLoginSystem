package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class GuestOnboardingActivity extends AppCompatActivity {

    private View indicator1, indicator2, indicator3;
    private ImageView featureImage;
    private TextView featureTitle;
    private MaterialButton nextButton;
    private TextView skipIntroText;

    // Track current page (1-based index)
    private int currentPage = 1;
    private final int TOTAL_PAGES = 3;

    // Arrays to hold content for each page
    private final int[] featureImages = {
            R.drawable.feature1_placeholder,
            R.drawable.feature2_placeholder,
            R.drawable.feature3_placeholder
    };

    private final String[] featureTitles = {
            "Feature 1",
            "Feature 2",
            "Feature 3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_onboarding);

        // Initialize views
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        featureImage = findViewById(R.id.featureImage);
        featureTitle = findViewById(R.id.featureTitle);
        nextButton = findViewById(R.id.nextButton);
        skipIntroText = findViewById(R.id.skipIntroText);

        // Set up listeners
        nextButton.setOnClickListener(v -> {
            if (currentPage < TOTAL_PAGES) {
                // Go to next page
                currentPage++;
                updatePageContent();
            } else {
                // On last page, proceed to success screen
                navigateToSuccessScreen();
            }
        });

        skipIntroText.setOnClickListener(v -> navigateToSuccessScreen());

        // Initialize with first page content
        updatePageContent();
    }

    private void updatePageContent() {
        // Update indicators based on current page
        updateIndicators();

        // Update content (image and text)
        featureImage.setImageResource(featureImages[currentPage - 1]);
        featureTitle.setText(featureTitles[currentPage - 1]);

        // Update button text for last page
        if (currentPage == TOTAL_PAGES) {
            nextButton.setText("Get Started");
        } else {
            nextButton.setText("Next");
        }
    }

    private void updateIndicators() {
        // Reset all indicators to inactive
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
        finishAffinity(); // Close all previous activities
    }
}