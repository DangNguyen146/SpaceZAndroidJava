package com.example.spacezandroidjava.Model;

public class CommentRequest {
    private String content;
    private int idProduct;
    private int idUser;
    public CommentRequest(String content,int idProduct,int idUser){
        this.content=content;
        this.idProduct=idProduct;
        this.idUser=idUser;

    }
}
