package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdjustAmount {
    public AdjustAmount(int idCart,int idProduct,int amount){
        this.idCart=idCart;
        this.idProduct=idProduct;
        this.amount=amount;

    }
    private int idCart;
    private int idProduct;
    private int amount;
}
