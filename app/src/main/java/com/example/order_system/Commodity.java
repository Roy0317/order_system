package com.example.order_system;

public class Commodity {
    String name;
    Boolean put;
    int count;
    String type;
    public Commodity(String name, Boolean put) {
        this.name = name;
        this.put = put;
    }

    public Commodity(String name, int currentCount){
        this.name=name;
        count = currentCount;
    }

    public String getName() {
        return name;
    }

    public Boolean getPut() {
        return put;
    }

    public String getType(){
        return type;
    }

    public void setCount(int count){
        this.count=count;
    }

}
class MainFood extends Commodity{
    int count;
    String sort;
    public MainFood(String name, Boolean put) {
        super(name, put);
    }

    public MainFood(String name, int currentCount){
        super(name, true);
        count = currentCount;
    }
    public void setCount(int count) {
        this.count = count;
    }

}
class SideFood extends Commodity{
    int count;
    public SideFood(String name, Boolean put) {
        super(name, put);
    }
    public SideFood(String name, int currentCount){
        super(name,true);
        count = currentCount;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
class Dessert extends Commodity{
    int count;
    public Dessert(String name, Boolean put) {
        super(name, put);
    }
    public Dessert(String name, int currentCount){
        super(name,true);
        count = currentCount;
    }
    public void setCount(int count) {
        this.count = count;
    }
}