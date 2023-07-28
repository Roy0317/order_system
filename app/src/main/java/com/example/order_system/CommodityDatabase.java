package com.example.order_system;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CommodityEntity.class}, version = 1, exportSchema = false)
public abstract class CommodityDatabase extends RoomDatabase {
    private static CommodityDatabase instance;

    public abstract CommodityDao commodityDao();

    public static synchronized CommodityDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CommodityDatabase.class, "commodity_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
