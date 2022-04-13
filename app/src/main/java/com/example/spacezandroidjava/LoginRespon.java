package com.example.spacezandroidjava;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRespon implements Serializable {
    private  int userId;
    private String token;
    private  String mess;
    private String firstName;
    private String lastName;


}
