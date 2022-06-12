package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequest {
    private String email;
    private  String otpCode;
    private String phone;
    public VerifyCodeRequest(String otp,String email,String phone){
        this.otpCode=otp;
        this.email=email;
        this.phone=phone;

    }
}
