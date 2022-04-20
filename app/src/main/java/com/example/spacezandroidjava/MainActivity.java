package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {

    LoginRespon loginRespon;
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        if (intent.getExtras()!=null){
            loginRespon = (LoginRespon) intent.getSerializableExtra("data");
            savePrefsData(loginRespon.getToken());
            savePrefFistNameLastName(loginRespon.getFirstName(),loginRespon.getLastName());
            savePrefUserId(loginRespon.getUserId());
            savePrefUsername(loginRespon.getUserName());
        }

        //assign variable
        bottomNavigation = findViewById(R.id.booton_navigation);

        //Add menu icon
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_nfc_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_account_box_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_mark_chat_unread_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_settings_applications_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // Initialize fragment
                Fragment fragment = null;
                //check condition
                switch (item.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new NFCFragment();
                        break;
                    case 3:


                        break;
                    case 4:
                        fragment = new MessFragment();
                        break;
                    case 5:
                        fragment = new SettingFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You click"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Reslected"+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        if(fragment==null){
            Intent i=getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.spacez");
            startActivity(i);

            return;

        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment).commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myToken",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isLogin",false);
        return  isIntroActivityOpnendBefore;
    }
    private void savePrefsData(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myToken",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isLogin",token);
        editor.commit();
    }
    private void savePrefFistNameLastName(String firstName,String lastName){
     SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
     SharedPreferences.Editor editor=pref.edit();
     editor.putString("firstName",firstName);
     editor.putString("lastName",lastName);
     editor.commit();

    }
    private void savePrefUsername(String userName){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("userName",userName);

        editor.commit();

    }
    private void savePrefUserId(int id){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("userId",id);
        editor.commit();

    }
}