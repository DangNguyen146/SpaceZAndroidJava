package com.example.spacezandroidjava;

import com.example.spacezandroidjava.Model.AddToCartRequest;
import com.example.spacezandroidjava.Model.AddToCartResponse;
import com.example.spacezandroidjava.Model.AdjustAmount;
import com.example.spacezandroidjava.Model.Cart;

import com.example.spacezandroidjava.Model.DeleteCartRequest;
import com.example.spacezandroidjava.Model.DeleteCartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("user/login")
    Call<LoginRespon> loginRespon(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<RegisterRespon> registerRespon(@Body RegisterRequest registerRequest);
    @POST("cart/add-to-cart")
    Call<AddToCartResponse> addToCartResponse(@Body AddToCartRequest addToCartRequest);
    @GET("cart/{id}")
    Call<List<Cart>> getMyCart(@Path("id") String id);
    @HTTP(method = "DELETE" ,path="cart/delete",hasBody = true)
    Call<DeleteCartResponse> deleteRequest(@Body DeleteCartRequest deleteCartRequest);
    @POST("cart/adjust")
    Call<Object> adjustAmount(@Body AdjustAmount adjustAmount);
}



