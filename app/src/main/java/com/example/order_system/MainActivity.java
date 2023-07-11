package com.example.order_system;

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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityFragment1 fragment1;
    private MainActivityFragment2 fragment2;
    private AlertDialog dialog;
    private BroadcastReceiver potatoReceiver;
    private int potatoCount;
    private int chickenCount;
    private int onionCount;
    private int donutCount;
    private int tartCount;
    private int iceCount;
    private static final String ITEM = "item";
    private static final String COUNT = "count";
    private static final String POTATOITEM_KEY="potato";
    private static final String CHICKENITEM_KEY="chicken";
    private static final String ONIONITEM_KEY="onion";
    private static final String DONUTITEM_KEY="donut";
    private static final String TARTITEM_KEY="tart";
    private static final String ICEITEM_KEY="ice";
    private static final String POTATOCOUNT_KEY="potato";
    private static final String CHICKENCOUNT_KEY="chicken";
    private static final String ONIONCOUNT_KEY="onion";
    private static final String DONUTCOUNT_KEY="donut";
    private static final String TARTCOUNT_KEY="tart";
    private static final String ICECOUNT_KEY="ice";
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences2;


    private MainFood p = null;
    private MainFood c = null;
    private SideFood o = null;
    private SideFood d = null;
    private Dessert t = null;
    private Dessert i= null;


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

        p = new MainFood("薯條", potatoCount);
        c = new MainFood("雞塊", chickenCount);
        o = new SideFood("洋蔥圈", onionCount);
        d = new SideFood("甜甜圈", donutCount);
        t = new Dessert("蛋塔", tartCount);
        i = new Dessert("甜筒", iceCount);

        //購物車資料
        sharedPreferences = getSharedPreferences(ITEM,MODE_PRIVATE);
        p.name = sharedPreferences.getString(POTATOITEM_KEY,null);
        c.name = sharedPreferences.getString(CHICKENITEM_KEY,null);
        o.name = sharedPreferences.getString(ONIONITEM_KEY,null);
        d.name = sharedPreferences.getString(DONUTITEM_KEY,null);
        t.name = sharedPreferences.getString(TARTITEM_KEY,null);
        i.name = sharedPreferences.getString(ICEITEM_KEY,null);

        sharedPreferences2 = getSharedPreferences(COUNT,MODE_PRIVATE);
        p.count = sharedPreferences2.getInt(POTATOCOUNT_KEY,0);
        c.count = sharedPreferences2.getInt(CHICKENCOUNT_KEY,0);
        o.count = sharedPreferences2.getInt(ONIONCOUNT_KEY,0);
        d.count = sharedPreferences2.getInt(DONUTCOUNT_KEY,0);
        t.count = sharedPreferences2.getInt(TARTCOUNT_KEY,0);
        i.count = sharedPreferences2.getInt(ICECOUNT_KEY,0);


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
        String[] commodityArray={p.name,c.name,o.name,d.name,t.name,i.name};
        int[] commodityArray2={p.count,c.count,o.count,d.count,t.count,i.count};
        String[] setected={"selectedPotato","selectedChicken","selectedOnion","selectedDonut","selectedTart","selectedIce"};
        String[] count={"potatoCount","chickenCount","onionCount","donutCount","tartCount","iceCount"};
        TextView title = new TextView(this);
        title.setText("購物車");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setTextColor(Color.BLACK);
        String message = "";

           for(int i=0;i<commodityArray.length;i++){
             if (commodityArray2[i]!=0){
                  message +=   commodityArray[i] + "，數量：" + commodityArray2[i] + "\n";
             }
         }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(POTATOITEM_KEY,p.name);
        editor.putString(CHICKENITEM_KEY,c.name);
        editor.putString(ONIONITEM_KEY,o.name);
        editor.putString(DONUTITEM_KEY,d.name);
        editor.putString(TARTITEM_KEY,t.name);
        editor.putString(ICEITEM_KEY,i.name);
        editor.apply();

        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putInt(POTATOCOUNT_KEY,p.count);
        editor2.putInt(CHICKENCOUNT_KEY,c.count);
        editor2.putInt(ONIONCOUNT_KEY,o.count);
        editor2.putInt(DONUTCOUNT_KEY,d.count);
        editor2.putInt(TARTCOUNT_KEY,t.count);
        editor2.putInt(ICECOUNT_KEY,i.count);
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

                for(int i=0;i<commodityArray.length;i++){
                    if (commodityArray2[i] != 0){
                        intent.putExtra(setected[i],commodityArray[i]);
                        intent.putExtra(count[i],commodityArray2[i]);
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
                // 例如，將資料設置到對話框的訊息中
                if (potato != null) {
                    potatoCount = count;
                    p = new MainFood("薯條", potatoCount);
                } else if (chicken != null) {
                    chickenCount = count2;
                    c = new MainFood("雞塊", chickenCount);
                } else if (onion !=null) {
                    onionCount = count3;
                    o = new SideFood("洋蔥圈", onionCount);
                } else if (donut !=null) {
                    donutCount = count4;
                    d = new SideFood("甜甜圈", donutCount);
                } else if (tart !=null) {
                    tartCount = count5;
                    t = new Dessert("蛋塔", tartCount);
                } else if (ice !=null) {
                    iceCount = count6;
                    i = new Dessert("甜筒", iceCount);
                }


                List<Commodity> commodityList =new ArrayList<>();
                commodityList.add(p);
                commodityList.add(c);
                commodityList.add(o);
                commodityList.add(d);
                commodityList.add(t);
                commodityList.add(i);

                for (int k=0;k<commodityList.size();k++){
                    if(commodityList.get(k) instanceof MainFood){
                        if(((MainFood) commodityList.get(k)).count>3){
                            ((MainFood) commodityList.get(k)).setCount(mistake(((MainFood) commodityList.get(k)).count));
                        }
                    }
                    if(commodityList.get(k) instanceof SideFood){
                        if(((SideFood) commodityList.get(k)).count>3){
                            ((SideFood) commodityList.get(k)).setCount(mistake(((SideFood) commodityList.get(k)).count));
                        }
                    }
                    if(commodityList.get(k) instanceof Dessert){
                        if (((Dessert) commodityList.get(k)).count>3){
                            ((Dessert) commodityList.get(k)).setCount(mistake(((Dessert) commodityList.get(k)).count));
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