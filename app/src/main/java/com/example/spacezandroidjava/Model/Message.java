package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String text;
    private String username;
    public Message(String username,String text){
        this.username=username;
        this.text=text;
    }

}
