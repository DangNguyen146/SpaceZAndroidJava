package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private int id;
    private int user1Id;
    private int user2Id;
    private  UserContact user;
}
