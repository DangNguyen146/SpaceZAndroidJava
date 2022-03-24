package com.example.spacezandroidjava.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product  implements Serializable {

    private  int id;
    @SerializedName("description")
    private String  description;
    private String name;
    private  String image;
    private  String imageSau;
    private String locatedText;

    private Long price;
    private Long view;
    private int rate;
    private Object category;
    private Object tag;

}
