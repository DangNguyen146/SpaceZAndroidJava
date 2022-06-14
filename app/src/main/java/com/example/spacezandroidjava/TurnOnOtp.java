package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.spacezandroidjava.Model.UserInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurnOnOtp extends AppCompatActivity {
    private boolean isOtp;

    private Switch changeOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_on_otp);

        Intent intent=getIntent();
        changeOtp = findViewById(R.id.switch1);

        if(intent.getExtras()!=null){
            UserInformation info= (UserInformation) intent.getSerializableExtra("userInfo");
            isOtp=info.getIsOtp();
            changeOtp.setChecked(isOtp);
        }
        changeOtp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Call<String> userInformationCall= ApiClient.getService().getChangeOtpResponse();
                    userInformationCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.i("userInfo", "true");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.i("userInfo", "onFailure: ",t);

                        }
                    });
                } else {
                    // The toggle is disabled
                }
            }
        });
    }
}