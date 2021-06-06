package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = getApplicationContext();

        /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, WelcomeActivity.class));
            }
        }, 2000);*/

        startActivity(new Intent(mContext, FoodMenuActivity.class));
        finish();
    }
}