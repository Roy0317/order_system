package com.example.order_system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "commodity_table")
public class CommodityEntity {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "sell")
    private Boolean sell;

    @ColumnInfo(name = "count")
    private int count;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "genreC")
    private String genreC;

    @ColumnInfo(name = "image")
    private String image;

    public void setCount(int count){
        this.count=count;
    }
    public int getCount() {
        return count;
    }
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getSell() {
        return sell;
    }

    public String getType() {
        return type;
    }

    public String getGenre() {
        return genre;
    }

    public String getGenreC() {
        return genreC;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSell(Boolean sell) {
        this.sell = sell;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setGenreC(String genreC) {
        this.genreC = genreC;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
