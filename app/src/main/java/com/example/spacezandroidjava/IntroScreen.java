package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroScreen extends AppCompatActivity {

    private ViewPager screenPaper;
    IntroViewPageDAPTER introViewPageDAPTER;
    TabLayout tabIndicator;
    Button btnNext;
    int position=0;
    Button btnStarted;
    Animation btnAnimation;
    TextView tvSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_intro_screen);

        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnStarted = findViewById(R.id.btnStarted);
        tabIndicator = findViewById(R.id.tab_indication);
        btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.botton_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Xu hướng kết nối chuyên nghiệp hiện đại", "Chạm thẻ SPACE vào điện thoại để chia sẻ thông tin", R.drawable.background2));
        mList.add(new ScreenItem("ĐA DẠNG THẺ\nTHỎA MÁI LỰA CHỌN", "Sự đa dạng, phong phú", R.drawable.background3));
        mList.add(new ScreenItem("Tạo ấn tượng với đối phương trong lần gặp mặt đầu tiên", "Ghi điểm ngay trong mắt người khác", R.drawable.background4));

        //setup viewPaper
        screenPaper = findViewById(R.id.screenView);
        introViewPageDAPTER = new IntroViewPageDAPTER(this, mList);
        screenPaper.setAdapter(introViewPageDAPTER);

        //setup tablayout with viewpaper
        tabIndicator.setupWithViewPager(screenPaper);

        //next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPaper.getCurrentItem();
                if(position<mList.size()){
                    position++;
                    screenPaper.setCurrentItem(position);
                }
                if(position==mList.size()-1){
                    btnNext.setVisibility(View.INVISIBLE);
                    btnStarted.setVisibility(View.VISIBLE);
                    tvSkip.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.INVISIBLE);

                    btnStarted.setAnimation(btnAnimation);
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {
                    btnNext.setVisibility(View.INVISIBLE);
                    btnStarted.setVisibility(View.VISIBLE);
                    tvSkip.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.INVISIBLE);

                    btnStarted.setAnimation(btnAnimation);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

                savePrefsData();
                finish();
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPaper.setCurrentItem(mList.size());
            }
        });

    }
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }
    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }
}
