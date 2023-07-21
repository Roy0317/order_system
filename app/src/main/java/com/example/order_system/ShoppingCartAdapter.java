package com.example.order_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.CartVH> {
    private Context context;
    private List<Commodity2> filtercommodity2;//保存大於1的商品


    //mvc mvp 設計模式
    public ShoppingCartAdapter(List<Commodity2> commodity2){
        this.filtercommodity2=new ArrayList<>();
        for(Commodity2 commodity:commodity2){
            if (commodity.count>0){
                filtercommodity2.add(commodity);
            }
        }
    }
    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item,parent,false);
        return new CartVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.textView.setText(filtercommodity2.get(position).genreC+filtercommodity2.get(position).name+"，數量:"+filtercommodity2.get(position).count);
        Glide.with(context).load(filtercommodity2.get(position).image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return filtercommodity2.size();
    }
    public class CartVH extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public CartVH(@NonNull View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.textFood);
            imageView=itemView.findViewById(R.id.imageFood);
        }
    }
}
