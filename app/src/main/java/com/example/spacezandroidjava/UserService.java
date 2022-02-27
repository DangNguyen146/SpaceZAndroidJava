package com.example.spacezandroidjava;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("user/login")
    Call<LoginRespon> loginRespon(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<RegisterRespon> registerRespon(@Body RegisterRequest registerRequest);
}
