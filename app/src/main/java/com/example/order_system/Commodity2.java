package com.example.order_system;

public class Commodity2 {
    String name;
    Boolean sell;
    int count;
    String type;
    String genre;
    String genreC;
    String image;
    public Commodity2(String name, Boolean sell) {
        this.name = name;
        this.sell = sell;
    }

    public Commodity2(String name, int currentCount){
        this.name=name;
        count = currentCount;
    }

    public Commodity2(String name, Boolean sell, int count, String type, String genre, String genreC) {
        this.name = name;
        this.sell = sell;
        this.count = count;
        this.type = type;
        this.genre = genre;
        this.genreC = genreC;
    }

    public String getName() {
        return name;
    }

    public Boolean getSell() {
        return sell;
    }

    public String getType(){
        return type;
    }

    public void setCount(int count){
        this.count=count;
    }
    public void setSell(boolean sell){
        this.sell=sell;
    }
    public String getGenre(){
        return genre;
    }
    public String getGenreC(){
        return genreC;
    }

    public int getCount() {
        return count;
    }
    public String getImage(){
        return image;
    }

}

