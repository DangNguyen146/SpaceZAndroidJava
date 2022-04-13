package com.example.spacezandroidjava.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart implements Serializable {

    private int id;
    private int price;
    private int amount;
    private int idProduct;
    private int idCart;
    @SerializedName("product")
    private Product product;




}
