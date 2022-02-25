package com.example.spacezandroidjava;

public class ScreenItem {
    String title, des;
    int ScreeenImg;

    public ScreenItem(String title, String des, int screeenImg) {
        this.title = title;
        this.des = des;
        ScreeenImg = screeenImg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setScreeenImg(int screeenImg) {
        ScreeenImg = screeenImg;
    }

    public String getTitle() {
        return title;
    }

    public String getDes() {
        return des;
    }

    public int getScreeenImg() {
        return ScreeenImg;
    }
}
