package com.example.order_system;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommodityUtil {

    //singleton 模式
    //java 回收機制
    //memory leak
    //mvc mvp
    //database 導入 (room)

    private static CommodityUtil instance;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "commodity_prefs";
    private static final String COMMODITY_LIST_KEY = "commodity_list";
    private static final String FOOD_NAME_KEY = "food_name";
    public static CommodityUtil getInstance(Context context){
        if(instance == null){
            instance = new CommodityUtil(context);
        }
        return instance;
    }

    public CommodityUtil(Context context){
        this.mContext = context.getApplicationContext();
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    }

    public void saveAllCommodities(List<Commodity2> commodities) {
        String commodityJson = new Gson().toJson(commodities);
        sharedPreferences.edit().putString(COMMODITY_LIST_KEY, commodityJson).apply();
    }

    public String getAllCommodities() {
        return sharedPreferences.getString(COMMODITY_LIST_KEY, null);
    }

    public void saveFoodName(List<String> foodName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(foodName);
        editor.putStringSet(FOOD_NAME_KEY, set);
        editor.apply();
    }

    public List<String> getFoodName() {
        Set<String> set = sharedPreferences.getStringSet(FOOD_NAME_KEY, null);
        if (set != null) {
            return new ArrayList<>(set);
        } else {
            return new ArrayList<>();
        }
    }
}
