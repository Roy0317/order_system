package com.example.order_system;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Commodity2 {
    String name;
    Boolean sell;
    int count;
    String type;
    String genre;
    String genreC;
    public Commodity2(String name, Boolean sell) {
        this.name = name;
        this.sell = sell;
    }

    public Commodity2(String name, int currentCount){
        this.name=name;
        count = currentCount;
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
}

