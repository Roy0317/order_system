package com.example.order_system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataParser {
    public static List<Commodity2> parseCommodityData(String data) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<ArrayList<Commodity2>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

}
