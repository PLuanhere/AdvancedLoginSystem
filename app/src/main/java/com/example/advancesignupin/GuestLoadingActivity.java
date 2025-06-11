package com.example.advancesignupin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuestLoadingActivity extends AppCompatActivity {

    private static final String TAG = "GuestLoadingActivity";
    private final int LOADING_TIME = 3000; // 3 segundos de loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_loading);

        // Log para ajudar na depuração
        Log.d(TAG, "GuestLoadingActivity started");

        // Use Handler com Looper explícito para compatibilidade com API
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "Loading complete, starting next activity");
                    // Use uma intenção explícita
                    Intent intent = new Intent(GuestLoadingActivity.this, GuestOnboardingActivity.class);
                    startActivity(intent);
                    finish(); // Fechar esta activity somente DEPOIS de iniciar a próxima
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to next screen", e);
                    e.printStackTrace();
                }
            }
        }, LOADING_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "GuestLoadingActivity destroyed");
    }
}