package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spacezandroidjava.Model.VerifyCodeRequest;
import com.example.spacezandroidjava.Model.VerifyCodeResponse;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeActivity extends AppCompatActivity {
    private OtpTextView otpTextView;
    private Button otpBtn;
    String otpCode;
    LoginRespon loginRespon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        otpTextView=(OtpTextView) findViewById(R.id.otp_view);
        Intent i=getIntent();
        if (i.getExtras()!=null){
            loginRespon = (LoginRespon) i.getSerializableExtra("data");


        }
        otpBtn=(Button) findViewById(R.id.btnOTP);
      otpBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              otpCode=otpTextView.getOTP();
              VerifyCodeRequest req=new VerifyCodeRequest(otpCode,loginRespon.getEmail(),loginRespon.getPhone());
              verifyCode(req);


          }
      });
    }
    public void verifyCode(VerifyCodeRequest verifyCodeRequest){
       Call<VerifyCodeResponse>verifyCodeResponseCall=ApiClient.getService().verifyCode(verifyCodeRequest);
       verifyCodeResponseCall.enqueue(new Callback<VerifyCodeResponse>() {
           @Override
           public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response) {
               VerifyCodeResponse res=response.body();
               startActivity(new Intent(VerifyCodeActivity.this,MainActivity.class).putExtra("data",res));
           }

           @Override
           public void onFailure(Call<VerifyCodeResponse> call, Throwable t) {

           }
       });


    }

}