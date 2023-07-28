package com.example.order_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.order_system.databinding.ActivityMainBinding;
import com.example.order_system.databinding.ActivityShoppingCartBinding;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private ActivityShoppingCartBinding binding;
    LinearLayoutManager layoutManager;
    private List<CommodityEntity> commodityEntities;
    ShoppingCartAdapter adapter;
    private CommodityDao commodityDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //初始化資料庫
        CommodityDatabase commodityDatabase = CommodityDatabase.getInstance(getApplicationContext());
        commodityDao = commodityDatabase.commodityDao();

        commodityEntities= new ArrayList<>();//初始化資料表
        refreshUIWithDatabaseData();//更新資料進commodityEntities


        layoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter=new ShoppingCartAdapter(commodityEntities);
        binding.recyclerView.setAdapter(adapter);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void refreshUIWithDatabaseData() {
        new ShoppingCart.LoadCommoditiesAsyncTask().execute();
    }

    private class LoadCommoditiesAsyncTask extends AsyncTask<Void, Void, List<CommodityEntity>> {
        @Override
        protected List<CommodityEntity> doInBackground(Void... voids) {
            return commodityDao.getAllCommodities();
        }

        @Override
        protected void onPostExecute(List<CommodityEntity> result) {
            super.onPostExecute(result);
            //把數量小於0的商品移除不要顯示
            for (int i=0;i<result.size();i++){
                if (result.get(i).getCount()==0){
                    result.remove(i);
                    i--;
                }
            }
            commodityEntities = result;
            adapter.updateData(commodityEntities);//更新recyclerview
        }

    }
}