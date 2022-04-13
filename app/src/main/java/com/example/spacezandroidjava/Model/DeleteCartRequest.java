package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCartRequest {
    public  DeleteCartRequest(int userId,int idProduct){
        this.idProduct=idProduct;
        this.userId=userId;

    }
    private int userId;
    private  int idProduct;
}
