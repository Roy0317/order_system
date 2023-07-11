package com.example.order_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingCart extends AppCompatActivity {
    private MainFood p = null;
    private MainFood c = null;
    private SideFood o = null;
    private SideFood d = null;
    private Dessert t = null;
    private Dessert i= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Button button=findViewById(R.id.button);
        TextView textView=findViewById(R.id.textView);

        ImageView[] imageViews = new ImageView[6];
        int[] imageViewIds = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6};

        Intent intent = getIntent();
        int potatoCount = intent.getIntExtra("potatoCount", 0);
        int chickenCount = intent.getIntExtra("chickenCount", 0);
        int onionCount = intent.getIntExtra("onionCount", 0);
        int donutCount = intent.getIntExtra("donutCount", 0);
        int tartCount = intent.getIntExtra("tartCount", 0);
        int iceCount = intent.getIntExtra("iceCount", 0);

        p = new MainFood("薯條", potatoCount);
        c = new MainFood("雞塊", chickenCount);
        o = new SideFood("洋蔥圈", onionCount);
        d = new SideFood("甜甜圈", donutCount);
        t = new Dessert("蛋塔", tartCount);
        i = new Dessert("甜筒", iceCount);


        String[] selected={"主餐:"+p.name,"主餐:"+c.name,"附餐:"+o.name,"附餐:"+d.name,"點心:"+t.name,"點心:"+i.name};
        int[] count={p.count,c.count,o.count,d.count,t.count,i.count};
        int[] imageArray = {R.drawable.potato, R.drawable.chicken, R.drawable.onion, R.drawable.donut, R.drawable.eggtart, R.drawable.ice};
        String message = "";
        for (int i=0;i<selected.length;i++){
            imageViews[i]=findViewById(imageViewIds[i]);
        }
        for(int i=0;i<selected.length;i++){
            if (count[i]!=0){
             message += selected[i] + "，數量" + count[i] + "\n"+"\n"+"\n"+"\n";
             Drawable drawable=getResources().getDrawable(imageArray[i]);
             imageViews[i].setImageDrawable(drawable);
            }
        }
        textView.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}