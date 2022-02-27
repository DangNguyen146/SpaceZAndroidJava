package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginScreen extends AppCompatActivity {
    TextInputLayout inputEmail, inputPassword;
    Button btnLogin;
    TextView singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.textInputLayout3);
        inputPassword = findViewById(R.id.textInputLayout2);
        singup = findViewById(R.id.titleRegister);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });


        inputEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    inputEmail.setError("Chưa nhập địa chỉ email");
                    inputEmail.setErrorEnabled(true);
                }
                else{
                    inputEmail.setError(null);
                    inputEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    inputPassword.setError("Chưa nhập mật khẩu");
                    inputPassword.setErrorEnabled(true);
                }
                else{
                    inputPassword.setError(null);
                    inputPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getEditText().getText().toString().trim();
                String pass = inputPassword.getEditText().getText().toString().trim();
                if (email.isEmpty()) {
                    inputEmail.setErrorEnabled(true);
                    inputEmail.setError("Chưa nhập địa chỉ email");
                }
                if (pass.isEmpty()) {
                    inputPassword.setErrorEnabled(true);
                    inputPassword.setError("Chưa nhập mật khẩu");
                }
            }
        });
    }
}