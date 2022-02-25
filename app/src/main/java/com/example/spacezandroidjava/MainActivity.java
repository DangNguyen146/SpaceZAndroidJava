package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2500;

    //Variable
    Animation topAnimation, bottonAnimation;
    ImageView imgV;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

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
                Intent intent = new Intent(MainActivity.this,LottieAnimation.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}