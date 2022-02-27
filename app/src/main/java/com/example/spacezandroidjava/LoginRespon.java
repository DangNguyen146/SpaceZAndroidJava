package com.example.spacezandroidjava;

import java.io.Serializable;

public class LoginRespon implements Serializable {
    private String token;
    private  String mess;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
