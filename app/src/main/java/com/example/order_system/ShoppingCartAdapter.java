package com.example.order_system;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.CartVH> {

    private List<Commodity2> commodity2;
    private Context context;

    public ShoppingCartAdapter(List<Commodity2> commodity2){
        this.commodity2=commodity2;
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
        holder.textView.setText(commodity2.get(position).genreC+commodity2.get(position).name+"數量 : "+commodity2.get(position).count);
        Glide.with(context).load(commodity2.get(position).image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return commodity2.size();
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
