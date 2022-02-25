package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    //Variable
    Animation topAnimation, bottonAnimation;
    ImageView imgV;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Animations
        topAnimation= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottonAnimation= AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        //Hooks
        imgV = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);

        imgV.setAnimation(topAnimation);
        logo.setAnimation(bottonAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LottieAnimation.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}