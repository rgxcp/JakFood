package com.rgxcp.jakfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    // Validasi get started
    private String mIsOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Menjalankan method
        isGetStartedOpened();

        // Membuat status bar dan navigation bar transparent
        Window mWindow = getWindow();
        mWindow.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Mengecek apakah user sudah membuka Get Started
        if (mIsOpened.equals("true")) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mGotoMain = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mGotoMain);
                    finish();
                }
            }, 2000);
        } else {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mGotoGetStarted = new Intent(SplashActivity.this, GetStartedActivity.class);
                    startActivity(mGotoGetStarted);
                    finish();
                }
            }, 2000);
        }
    }

    // Method mengecek apakah get started sudah dibuka
    private void isGetStartedOpened() {
        String GET_STARTED_KEY = "get_started_key";
        String GetStartedKeyArgs = "";
        SharedPreferences mSharedPreferences = getSharedPreferences(GET_STARTED_KEY, Context.MODE_PRIVATE);
        mIsOpened = mSharedPreferences.getString(GetStartedKeyArgs, "");
    }
}
