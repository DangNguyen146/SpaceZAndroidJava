package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserInformationRequest {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    public ChangeUserInformationRequest(String email,String username,String firstName,String lastName){
        this.email=email;
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;


    }

}
