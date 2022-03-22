package com.example.spacezandroidjava.Model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product  implements Serializable {

    private  int id;
    private String name;
    private  String image;
    private  String imageSau;
    private String locatedText;
    private int categoryId;
    private int idTypes;

}
