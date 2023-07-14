package com.example.order_system;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private MainActivityFragment1 fragment1;
    private MainActivityFragment2 fragment2;
    private AlertDialog dialog;
    private BroadcastReceiver potatoReceiver;
    private static final String COUNT = "count";
    private static final String POTATOCOUNT_KEY="potato";
    private static final String CHICKENCOUNT_KEY="chicken";
    private static final String ONIONCOUNT_KEY="onion";
    private static final String DONUTCOUNT_KEY="donut";
    private static final String TARTCOUNT_KEY="tart";
    private static final String ICECOUNT_KEY="ice";
    private SharedPreferences sharedPreferences2;
    private List<Commodity2> commodity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton1 = findViewById(R.id.imagebutton1);
        ImageButton imageButton2 = findViewById(R.id.imagebutton2);
        ImageView shoppingcart = findViewById(R.id.imageView);


        imageButton1.setColorFilter(Color.RED);
        fragment1 = new MainActivityFragment1();
        fragment2 = new MainActivityFragment2();

        brod();
        //點餐,後台
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout1,fragment1);
        fragmentTransaction.add(R.id.frameLayout1,fragment2);
        fragmentTransaction.show(fragment1);
        fragmentTransaction.hide(fragment2);
        fragmentTransaction.commit();

        //json
        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getApplication());
        //購物車資料

        sharedPreferences2 = getSharedPreferences(COUNT,MODE_PRIVATE);
        commodity2.get(0).count = sharedPreferences2.getInt(POTATOCOUNT_KEY,0);
        commodity2.get(1).count = sharedPreferences2.getInt(CHICKENCOUNT_KEY,0);
        commodity2.get(2).count = sharedPreferences2.getInt(ONIONCOUNT_KEY,0);
        commodity2.get(3).count = sharedPreferences2.getInt(DONUTCOUNT_KEY,0);
        commodity2.get(4).count = sharedPreferences2.getInt(TARTCOUNT_KEY,0);
        commodity2.get(5).count = sharedPreferences2.getInt(ICECOUNT_KEY,0);


        //點餐紐 後台鈕
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton1.setColorFilter(null);
                imageButton2.setColorFilter(null);
                if(view==imageButton1){
                    imageButton1.setColorFilter(Color.RED);
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.show(fragment1);
                    fragmentTransaction.hide(fragment2);
                    fragmentTransaction.commit();
                }else{
                    imageButton2.setColorFilter(Color.RED);
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.hide(fragment1);
                    fragmentTransaction.show(fragment2);
                    fragmentTransaction.commit();
                }
            }
        };
        imageButton1.setOnClickListener(clickListener);
        imageButton2.setOnClickListener(clickListener);


        shoppingcart.setOnClickListener(new View.OnClickListener() {
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
        String[] setected={"selectedPotato","selectedChicken","selectedOnion","selectedDonut","selectedTart","selectedIce"};
        String[] count={"potatoCount","chickenCount","onionCount","donutCount","tartCount","iceCount"};
        TextView title = new TextView(this);
        title.setText("購物車");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setTextColor(Color.BLACK);
        String message = "";

           for(int i=0;i<commodity2.size();i++){
             if (commodity2.get(i).count!=0){
                  message +=   commodity2.get(i).name + "，數量：" + commodity2.get(i).count + "\n";
             }
         }

        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putInt(POTATOCOUNT_KEY,commodity2.get(0).count);
        editor2.putInt(CHICKENCOUNT_KEY,commodity2.get(1).count);
        editor2.putInt(ONIONCOUNT_KEY,commodity2.get(2).count);
        editor2.putInt(DONUTCOUNT_KEY,commodity2.get(3).count);
        editor2.putInt(TARTCOUNT_KEY,commodity2.get(4).count);
        editor2.putInt(ICECOUNT_KEY,commodity2.get(5).count);
        editor2.apply();

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

                for(int i=0;i<commodity2.size();i++){
                    if (commodity2.get(i).count != 0){
                        intent.putExtra(setected[i],commodity2.get(i).type);
                        intent.putExtra(count[i],commodity2.get(i).count);
                    }
                }
                startActivity(intent);
            }
        });
        dialog = builder.create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity被銷毀時解除註冊BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(potatoReceiver);
    }

    private void brod(){
        // 建立接收廣播的BroadcastReceiver
        potatoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String potato = intent.getStringExtra("potato");
                String chicken = intent.getStringExtra("chicken");
                String onion = intent.getStringExtra("onion");
                String donut =intent.getStringExtra("donut");
                String tart =intent.getStringExtra("tart");
                String ice =intent.getStringExtra("ice");
                int count = intent.getIntExtra("count", 0);
                int count2 = intent.getIntExtra("count2",0);
                int count3 = intent.getIntExtra("count3",0);
                int count4 = intent.getIntExtra("count4",0);
                int count5 = intent.getIntExtra("count5",0);
                int count6 = intent.getIntExtra("count6",0);
                // 在這裡處理接收到的資料
                if (potato != null) {
                    commodity2.get(0).count = count;
                } else if (chicken != null) {
                    commodity2.get(1).count = count2;
                } else if (onion !=null) {
                    commodity2.get(2).count = count3;
                } else if (donut !=null) {
                    commodity2.get(3).count = count4;
                } else if (tart !=null) {
                    commodity2.get(4).count = count5;
                } else if (ice !=null) {
                    commodity2.get(5).count = count6;
                }
                //處理數量超過3
                for (int k=0;k<commodity2.size();k++){
                    if(commodity2.get(k).genre.equals("MainFood")){
                        if(commodity2.get(k).count>3){
                            commodity2.get(k).setCount(mistake(commodity2.get(k).count));
                        }
                    }
                    if(commodity2.get(k).genre.equals("SideFood")){
                        if(commodity2.get(k).count>3){
                            commodity2.get(k).setCount(mistake(commodity2.get(k).count));
                        }
                    }
                    if(commodity2.get(k).genre.equals("Dessert")){
                        if (commodity2.get(k).count>3){
                            commodity2.get(k).setCount(mistake(commodity2.get(k).count));
                        }
                    }
                }
                createDialog();
            }
        };
        // 註冊BroadcastReceiver並設置過濾器
        IntentFilter filter = new IntentFilter("com.example.order_system.POTATO_SELECTED1");
        IntentFilter filter2 = new IntentFilter("com.example.order_system.POTATO_SELECTED2");
        IntentFilter filter3 = new IntentFilter("com.example.order_system.POTATO_SELECTED3");
        IntentFilter filter4 = new IntentFilter("com.example.order_system.POTATO_SELECTED4");
        IntentFilter filter5 = new IntentFilter("com.example.order_system.POTATO_SELECTED5");
        IntentFilter filter6 = new IntentFilter("com.example.order_system.POTATO_SELECTED6");
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter);
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter2);
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter3);
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter4);
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter5);
        LocalBroadcastManager.getInstance(this).registerReceiver(potatoReceiver, filter6);
    }
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
}