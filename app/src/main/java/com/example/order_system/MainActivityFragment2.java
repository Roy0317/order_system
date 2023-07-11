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

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment2 extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "click";
    private MainFood p = new MainFood("potato", true);
    private MainFood c = new MainFood("chicken", true);
    private SideFood o = new SideFood("onion", true);
    private SideFood d = new SideFood("donut", true);
    private Dessert t = new Dessert("tart", true);
    private Dessert ii = new Dessert("ice", true);

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

        for (int i=0;i<click.length;i++){
            textViews[i]=view.findViewById(textViewIds[i]);
            up(textViews, i);
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
        editor.apply();

        if(k==true){
            textView.setBackgroundColor(Color.GRAY);
            textView.setText("已上架");
        }
    }
    private void up(TextView[] textViews,int num){
        List<Commodity> commodityList =new ArrayList<>();
        commodityList.add(p);
        commodityList.add(c);
        commodityList.add(o);
        commodityList.add(d);
        commodityList.add(t);
        commodityList.add(ii);
        textViews[num].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commodityList.get(num) instanceof MainFood){
                    String name=(commodityList.get(num)).name;
                    MainFood mainFood=new MainFood(name,true);
                    handleFoodClick(name,textViews[num],mainFood.getPut());
                }else if (commodityList.get(num) instanceof SideFood){
                    String name=(commodityList.get(num)).name;
                    SideFood sideFood=new SideFood(name,true);
                    handleFoodClick(name,textViews[num],sideFood.getPut());
                } else if (commodityList.get(num) instanceof Dessert) {
                    String name=(commodityList.get(num)).name;
                    Dessert dessert=new Dessert(name,true);
                    handleFoodClick(name,textViews[num],dessert.getPut());
                }
            }
        });
    }
}
