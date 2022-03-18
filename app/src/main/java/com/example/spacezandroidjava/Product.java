package com.example.spacezandroidjava;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
        public Product(String name,String description){
                this.name=name;
                this.description=description;

        }
        private  String name;
        private String description;

}
