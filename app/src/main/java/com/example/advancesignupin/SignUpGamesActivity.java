package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SignUpGamesActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button continueButton;
    private TextView skipStepButton;
    private List<MaterialCardView> gameCards = new ArrayList<>();
    private List<Integer> selectedGames = new ArrayList<>();
    private final int SELECTED_LIMIT = 3;

    private String userEmail;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_games);

        // Get data from previous steps
        userEmail = getIntent().getStringExtra("EMAIL");
        username = getIntent().getStringExtra("USERNAME");

        // Initialize views
        searchInput = findViewById(R.id.searchInput);
        continueButton = findViewById(R.id.continueButton);
        skipStepButton = findViewById(R.id.skipStepButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Initialize game cards
        gameCards.add(findViewById(R.id.game1Card));
        gameCards.add(findViewById(R.id.game2Card));
        gameCards.add(findViewById(R.id.game3Card));
        gameCards.add(findViewById(R.id.game4Card));
        gameCards.add(findViewById(R.id.game5Card));
        gameCards.add(findViewById(R.id.game6Card));

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up search input focus change
        searchInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchInput.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                searchInput.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });

        // Add text watcher to search input
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter games based on search text
                // This would be implemented to filter the actual game list
                if (s.length() > 0) {
                    Toast.makeText(SignUpGamesActivity.this, "Searching: " + s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up game card selection
        for (int i = 0; i < gameCards.size(); i++) {
            final int gameIndex = i;
            MaterialCardView card = gameCards.get(i);
            card.setOnClickListener(v -> {
                toggleGameSelection(card, gameIndex);
            });
        }

        // Set up continue button
        continueButton.setOnClickListener(v -> {
            if (selectedGames.size() > 0) {
                proceedToLoadingScreen();
            } else {
                Toast.makeText(SignUpGamesActivity.this, "Please select at least one game", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up skip button
        skipStepButton.setOnClickListener(v -> proceedToLoadingScreen());
    }

    private void toggleGameSelection(MaterialCardView card, int gameIndex) {
        if (selectedGames.contains(gameIndex)) {
            // Deselect game
            selectedGames.remove(Integer.valueOf(gameIndex));
            card.setStrokeWidth(0);
        } else {
            // Select game
            if (selectedGames.size() < SELECTED_LIMIT) {
                selectedGames.add(gameIndex);
                card.setStrokeColor(getResources().getColor(R.color.orange));
                card.setStrokeWidth(4);
            } else {
                Toast.makeText(this, "You can select up to " + SELECTED_LIMIT + " games", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void proceedToLoadingScreen() {
        setContentView(R.layout.loading_screen);

        String displayName = username != null ? username : "Gamer";
        TextView loadingMessage = findViewById(R.id.loadingMessage);
        loadingMessage.setText("Great choices, " + displayName + "!");

        // Simulate loading with a delay
        new Handler().postDelayed(() -> {
            // Navigate to success page
            Intent intent = new Intent(SignUpGamesActivity.this, SignUpSuccessActivity.class);
            intent.putExtra("EMAIL", userEmail);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        }, 2000);
    }
}