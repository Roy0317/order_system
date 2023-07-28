package com.example.order_system;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 使用 AsyncTask 在背景執行緒上執行資料庫初始化檢查
        new InitializeDatabaseTask().execute();
    }

    private void initializeDatabase() {
        CommodityDatabase commodityDatabase = CommodityDatabase.getInstance(getApplicationContext());
        CommodityDao commodityDao = commodityDatabase.commodityDao();

        if (!hasInsertedData(commodityDao)) {
            // 獲取初始資料
            List<Commodity2> initialCommodities = new JsonHelper().jsonget(getApplicationContext());

            // 將初始資料轉換為CommodityEntity並插入資料庫
            List<CommodityEntity> commodityEntities = new ArrayList<>();

            for (Commodity2 commodity2 : initialCommodities) {
                CommodityEntity commodityEntity = new CommodityEntity();
                commodityEntity.setName(commodity2.name);
                commodityEntity.setSell(commodity2.sell);
                commodityEntity.setCount(commodity2.count);
                commodityEntity.setType(commodity2.type);
                commodityEntity.setGenre(commodity2.genre);
                commodityEntity.setGenreC(commodity2.genreC);
                commodityEntity.setImage(commodity2.image);
                commodityEntities.add(commodityEntity);
            }

            commodityDao.insertCommodities(commodityEntities.toArray(new CommodityEntity[0]));
        }
    }

    private boolean hasInsertedData(CommodityDao commodityDao) {
        // 檢查資料庫是否已有資料
        return commodityDao.getCommodityCount() > 0;
    }

    private class InitializeDatabaseTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            initializeDatabase();
            return null;
        }
    }
}
