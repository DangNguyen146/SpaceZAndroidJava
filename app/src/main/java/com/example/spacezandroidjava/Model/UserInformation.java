package com.example.spacezandroidjava.Model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformation implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private Boolean isOtp;
    private String avartar;

}
