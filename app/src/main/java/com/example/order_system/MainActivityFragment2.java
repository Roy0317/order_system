package com.example.order_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment2 extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "click";
    private List<Commodity2> commodity2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment2, container, false);
        TextView[] textViews = new TextView[6];
        int[] textViewIds = {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6};

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        String clickPotato = sharedPreferences.getString("POTATO_KEY", null);
        String clickChicken = sharedPreferences.getString("CHICKEN_KEY", null);
        String clickOnion = sharedPreferences.getString("ONION_KEY", null);
        String clickDonut = sharedPreferences.getString("DONUT_KEY", null);
        String clickTart = sharedPreferences.getString("TART_KEY", null);
        String clickIce = sharedPreferences.getString("ICE_KEY", null);
        String[] click={clickPotato,clickChicken,clickOnion,clickDonut,clickTart,clickIce};

        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getActivity().getApplication());

        for (int i=0;i<commodity2.size();i++){
            textViews[i]=view.findViewById(textViewIds[i]);
            up(textViews,i);
        }

        for (int i=0;i<click.length;i++){
            if(click[i]!=null){
                textViews[i].setText("已上架");
                textViews[i].setBackgroundColor(Color.GRAY);
            }
        }
        return view;
    }

    private void handleFoodClick(String foodName,TextView textView ,boolean k){
        Intent intent = new Intent("com.example.ACTION_"+foodName.toUpperCase());
        intent.putExtra(foodName,foodName);
        getContext().sendBroadcast(intent);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(foodName.toUpperCase()+"_KEY",foodName);
        Log.d("TGG", "handleFoodClick: "+foodName.toUpperCase());
        editor.apply();

        if(k==true){
            textView.setBackgroundColor(Color.GRAY);
            textView.setText("已上架");
        }
    }
    private void up(TextView[] textViews,int num){
        textViews[num].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commodity2.get(num).genre.equals("MainFood")){
                    String name=commodity2.get(num).type;
                    commodity2.get(num).setSell(true);
                    handleFoodClick(name,textViews[num],commodity2.get(num).sell);
                }else if (commodity2.get(num).genre.equals("SideFood")){
                    String name=commodity2.get(num).type;
                    commodity2.get(num).setSell(true);
                    handleFoodClick(name,textViews[num],commodity2.get(num).sell);
                } else if (commodity2.get(num).genre.equals("Dessert")) {
                    String name=commodity2.get(num).type;
                    commodity2.get(num).setSell(true);
                    handleFoodClick(name,textViews[num],commodity2.get(num).sell);
                }
            }
        });
    }
}
