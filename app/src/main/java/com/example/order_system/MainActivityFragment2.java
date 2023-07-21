package com.example.order_system;

import android.content.Context;
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

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MainActivityFragment2 extends Fragment {
    private List<Commodity2> commodity2;
    private CommodityUtil commodityUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment2, container, false);
        TextView[] textViews = new TextView[6];
        int[] textViewIds = {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6};
        commodityUtil = CommodityUtil.getInstance(requireActivity().getApplication());//初始化sharedpreferences
        //Json
        commodity2 = new JsonHelper().jsonget(getActivity().getApplication());

        String savedCommodityJson=commodityUtil.getAllCommodities(); //讀取儲存的資料

        for (int i=0;i<commodity2.size();i++){
            textViews[i]=view.findViewById(textViewIds[i]);
        }



        if(savedCommodityJson!=null){
            //若有資料被儲存 把資料傳回給commodity2 並判斷有沒有被上架
            commodity2 =DataParser.parseCommodityData(savedCommodityJson);
            for (int i=0;i<commodity2.size();i++){
                if(commodity2.get(i).sell.equals(true)){
                    textViews[i].setText("已上架");
                    textViews[i].setBackgroundColor(Color.GRAY);
                }
            }
        }else{
            //若沒資料則初始化
            commodity2 = new JsonHelper().jsonget(getActivity().getApplication());
        }

        for (int i=0;i<commodity2.size();i++){
            up(textViews,i);
        }
        return view;
    }

    private void handleFoodClick(String foodName,TextView textView){
        EventBus.getDefault().post(new FoodClickedEvent(foodName));

        //若有被上架就儲存一遍
        for(int i=0;i<commodity2.size();i++){
            if(commodity2.get(i).type.equals(foodName)){
                commodity2.get(i).setSell(true);
                commodityUtil.saveAllCommodities(commodity2);//儲存資料
            }
        }
        textView.setBackgroundColor(Color.GRAY);
        textView.setText("已上架");
    }
    private void up(TextView[] textViews,int num){
        textViews[num].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commodity2.get(num).genre.equals("main_course") || commodity2.get(num).genre.equals("side_dish") || commodity2.get(num).genre.equals("dessert")){
                    String name=commodity2.get(num).type;
                    handleFoodClick(name,textViews[num]);
                }
            }
        });
    }
    public class FoodClickedEvent{
        private String foodName;
        public FoodClickedEvent(String foodname){
            this.foodName=foodname;
        }
        public String getFoodName(){
            return foodName;
        }
    }
}