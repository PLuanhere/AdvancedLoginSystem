package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyPasswordCodeActivity extends AppCompatActivity {

    private TextView confirmationSentText;
    private EditText verificationCodeInput;
    private TextView resendButton;
    private TextView errorMessageText;
    private Button continueButton;
    private String userEmail;

    private boolean canResend = true;
    private CountDownTimer resendTimer;
    private static final int RESEND_COOLDOWN_SECONDS = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_password_code);

        // Get the email from the previous activity
        userEmail = getIntent().getStringExtra("EMAIL");
        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = "example123@gmail.com"; // Default for testing
        }

        // Initialize views
        confirmationSentText = findViewById(R.id.confirmationSentText);
        verificationCodeInput = findViewById(R.id.verificationCodeInput);
        resendButton = findViewById(R.id.resendButton);
        errorMessageText = findViewById(R.id.errorMessageText);
        continueButton = findViewById(R.id.continueButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up the email text
        confirmationSentText.setText(String.format("A password reset code was sent to\n%s", userEmail));

        // Add focus change listener to the verification code input
        verificationCodeInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                verificationCodeInput.setBackground(getResources().getDrawable(R.drawable.purple_outlined_background));
            } else {
                verificationCodeInput.setBackground(getResources().getDrawable(R.drawable.input_rounded_background));
            }
        });

        // Add text watcher to verification code input
        verificationCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable continue button when there's text
                continueButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up resend button
        resendButton.setOnClickListener(v -> {
            if (canResend) {
                // Create a formatted message with different text colors
                String preText = "Code resent to ";
                String fullMessage = preText + userEmail;

                // Create a SpannableString to apply different colors
                SpannableString spannableString = new SpannableString(fullMessage);

                // Set the base color as light gray
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray)),
                        0, preText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // Set the email part to white
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)),
                        preText.length(), fullMessage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // Show confirmation message
                Toast.makeText(this, "Verification code resent", Toast.LENGTH_SHORT).show();

                // Start cooldown
                startResendCooldown();
            }
        });

        // Set up continue button
        continueButton.setOnClickListener(v -> {
            String code = verificationCodeInput.getText().toString().trim();
            if (code.isEmpty()) {
                Toast.makeText(this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            } else if (code.length() < 6) {
                Toast.makeText(this, "Please enter a valid 6-digit code", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to reset password screen
                Intent intent = new Intent(VerifyPasswordCodeActivity.this, ResetPasswordActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }

    private void startResendCooldown() {
        canResend = false;

        if (resendTimer != null) {
            resendTimer.cancel();
        }

        resendTimer = new CountDownTimer(RESEND_COOLDOWN_SECONDS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                int minutes = secondsLeft / 60;
                int seconds = secondsLeft % 60;

                String timeLeftFormatted = String.format("Please wait %02d:%02d before resending", minutes, seconds);
                errorMessageText.setText(timeLeftFormatted);
                errorMessageText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                canResend = true;
                errorMessageText.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) {
            resendTimer.cancel();
        }
    }
}