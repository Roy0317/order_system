package com.example.order_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private List<Commodity2> commodity2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Button button=findViewById(R.id.button);
        TextView textView=findViewById(R.id.textView);

        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getApplication());

        ImageView[] imageViews = new ImageView[6];
        int[] imageViewIds = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6};

        Intent intent = getIntent();
        int potatoCount = intent.getIntExtra("potatoCount", 0);
        int chickenCount = intent.getIntExtra("chickenCount", 0);
        int onionCount = intent.getIntExtra("onionCount", 0);
        int donutCount = intent.getIntExtra("donutCount", 0);
        int tartCount = intent.getIntExtra("tartCount", 0);
        int iceCount = intent.getIntExtra("iceCount", 0);

        int[] count={potatoCount,chickenCount,onionCount,donutCount,tartCount,iceCount};
        int[] imageArray = {R.drawable.potato, R.drawable.chicken, R.drawable.onion, R.drawable.donut, R.drawable.eggtart, R.drawable.ice};
        String message = "";
        for (int i=0;i<commodity2.size();i++){
            imageViews[i]=findViewById(imageViewIds[i]);
        }
        for(int i=0;i<commodity2.size();i++){
            if (count[i]!=0){
             message += commodity2.get(i).genreC+ commodity2.get(i).name + "，數量" + count[i] + "\n"+"\n"+"\n"+"\n";
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