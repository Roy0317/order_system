package com.example.order_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.order_system.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import kotlinx.coroutines.GlobalScope;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityFragment1 fragment1;
    private MainActivityFragment2 fragment2;
    private AlertDialog dialog;
    private List<Commodity2> commodity2;
    private List<Commodity2> commodity2car=new ArrayList<>(); //要傳給購物車的list
    private CommodityUtil commodityUtil;
    private CommodityDao commodityDao;
    private List<CommodityEntity> commodityEntities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EventBus.getDefault().register(this);

        commodityUtil = CommodityUtil.getInstance(getApplicationContext());//初始化sharedpreferences

        //初始化資料庫
        CommodityDatabase commodityDatabase = CommodityDatabase.getInstance(getApplicationContext());
        commodityDao = commodityDatabase.commodityDao();

        binding.imagebutton1.setColorFilter(Color.RED);
        fragment1 = new MainActivityFragment1();
        fragment2 = new MainActivityFragment2();


        //點餐,後台
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout1,fragment1);
        fragmentTransaction.add(R.id.frameLayout1,fragment2);
        fragmentTransaction.show(fragment1);
        fragmentTransaction.hide(fragment2);
        fragmentTransaction.commit();

        //json
        commodity2 = new JsonHelper().jsonget(getApplication());

        //購物車資料
        String savedCommodityJson=commodityUtil.getAllCommodities();//SharedPreferences加载保存的資料

        if (savedCommodityJson != null) {
            // 如果有保存的資料，轉換回Commodity2的列表
            commodity2 =DataParser.parseCommodityData(savedCommodityJson);
        } else {
            // 如果没有保存的資料，從JSONHelper初始化commodity2列表
            commodity2 = new JsonHelper().jsonget(getApplication());
        }
        //點餐紐 後台鈕
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imagebutton1.setColorFilter(null);
                binding.imagebutton2.setColorFilter(null);
                if(view==binding.imagebutton1){
                    binding.imagebutton1.setColorFilter(Color.RED);
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.show(fragment1);
                    fragmentTransaction.hide(fragment2);
                    fragmentTransaction.commit();
                }else{
                    binding.imagebutton2.setColorFilter(Color.RED);
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.hide(fragment1);
                    fragmentTransaction.show(fragment2);
                    fragmentTransaction.commit();
                }
            }
        };
        binding.imagebutton1.setOnClickListener(clickListener);
        binding.imagebutton2.setOnClickListener(clickListener);

        commodityEntities = new ArrayList<>();//初始化資料表
        refreshUIWithDatabaseData();//更新資料進commodityEntities

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.show();
                }else{
                    createDialog();
                }
            }
        });
    }

    //購物車Dialog
    private void createDialog() {
        TextView title = new TextView(this);
        title.setText("購物車");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setTextColor(Color.BLACK);
        String message = "";
           //顯示數量
           for(int i=0;i<commodity2.size();i++){
             if (commodity2.get(i).count!=0){
                  message += commodity2.get(i).name + "，數量：" + commodity2.get(i).count + "\n";
             }
         }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(true)
                .setCustomTitle(title)
                .setMessage(message);
        //進入結帳
        builder.setPositiveButton("進入結帳畫面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent();
                intent.setClass(MainActivity.this, ShoppingCart.class);
                String savedCommodityJson=commodityUtil.getAllCommodities();
                commodity2car=DataParser.parseCommodityData(savedCommodityJson);
                for (int i=0;i<commodity2car.size();i++){
                    if (commodity2car.get(i).count==0){
                        commodity2car.remove(i);
                        i--;
                    }
                }
                String commodityJsoncar = new Gson().toJson(commodity2car);
                intent.putExtra("cardata",commodityJsoncar);
                startActivity(intent);

            }
        });
        dialog = builder.create();
    }
    //接收點擊
    public void onFoodClick(Bundle bundle) {
        String food = bundle.getString("food");
        for(int i=0;i<commodity2.size();i++) {
            if(commodity2.get(i).type.equals(food)){
                commodity2.get(i).count++;

                //檢查是否超過3份
                if (commodity2.get(i).count>3){
                    commodity2.get(i).setCount(mistake(commodity2.get(i).count));
                }

                //更新資料庫裡的count
                int count = commodityEntities.get(i).getCount() + 1;
                if(commodityEntities.get(i).getCount()<3){
                    commodityEntities.get(i).setCount(count);
                }
            }
        }
        commodityUtil.saveAllCommodities(commodity2);//保存資料

        //更新資料
        new UpdateCommoditiesAsyncTask().execute(commodityEntities.toArray(new CommodityEntity[0]));

        // 更新UI：重新讀取資料庫的資料並更新UI
        refreshUIWithDatabaseData();
        createDialog();
    }

   //超過3個
    private int mistake(int k){
        k=3;
        android.app.AlertDialog.Builder alertDialog =
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setCancelable(true)
                        .setTitle("錯誤")
                        .setMessage("請誤點超過3份");
        alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
        return k;
    }
    @Subscribe
    public void onFoodClicked(MainActivityFragment2.FoodClickedEvent event) {
        String foodName = event.getFoodName();
        // 在这里处理接收到的值
        Log.d("MainActivity", "Received food name: " + foodName);
    }
    private class UpdateCommoditiesAsyncTask extends AsyncTask<CommodityEntity, Void, Void> {
        @Override
        protected Void doInBackground(CommodityEntity... commodityEntities) {
            commodityDao.updateCommodity(commodityEntities);
            return null;
        }
    }
    private void refreshUIWithDatabaseData() {
        new LoadCommoditiesAsyncTask().execute();
    }

    private class LoadCommoditiesAsyncTask extends AsyncTask<Void, Void, List<CommodityEntity>> {
        @Override
        protected List<CommodityEntity> doInBackground(Void... voids) {
            return commodityDao.getAllCommodities();
        }

        @Override
        protected void onPostExecute(List<CommodityEntity> result) {
            super.onPostExecute(result);
            commodityEntities = result;
        }

    }
}