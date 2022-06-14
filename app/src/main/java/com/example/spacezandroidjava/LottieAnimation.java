package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class LottieAnimation extends AppCompatActivity {

    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_animation);

        lottie = findViewById(R.id.lottie);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (restorePrefData()) {
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class );
                    startActivity(mainActivity);
                    finish();
                } else{
                    Intent i = new Intent(getApplicationContext(), IntroScreen.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 3000);
    }
    private boolean restorePrefData() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myToken",MODE_PRIVATE);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String temp;

        String isIntroActivityOpnendBefore = pref.getString("isLogin", "");


        return !isIntroActivityOpnendBefore.isEmpty();
    }
}