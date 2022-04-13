package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    public  AddToCartRequest(int userId,int idProduct){
        this.userId=userId;
        this.idProduct=idProduct;


    }
    private int userId;
    private int idProduct;



}
