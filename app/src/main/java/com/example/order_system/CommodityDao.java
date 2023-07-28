package com.example.order_system;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommodityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommodity(CommodityEntity commodityEntity);

    @Update
    void updateCommodity(CommodityEntity... commodities);

    @Query("SELECT * FROM commodity_table")
    List<CommodityEntity> getAllCommodities();
    @Insert
    void insertCommodities(CommodityEntity... commodityEntities);
    @Query("SELECT COUNT(*) FROM commodity_table")
    int getCommodityCount();
}
