package com.example.spacezandroidjava.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactRequest {
    int user1Id;
    int user2Id;

    public CreateContactRequest(int user1Id, int user2Id) {
        this.user1Id=user1Id;
        this.user2Id=user2Id;
    }
}
