package com.example.spacezandroidjava;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
@GET("product")
    Call<List<Product>>getProduct();
}
