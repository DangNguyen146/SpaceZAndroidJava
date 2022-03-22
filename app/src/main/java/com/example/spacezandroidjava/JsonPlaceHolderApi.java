package com.example.spacezandroidjava;

import com.example.spacezandroidjava.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
@GET("product")
    Call<List<Product>>getProduct();
}
