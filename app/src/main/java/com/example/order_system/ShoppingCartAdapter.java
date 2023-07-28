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
import com.example.order_system.databinding.CartItemBinding;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.CartVH> {

    private List<CommodityEntity> commodityEntities;
    private Context context;
    public void updateData(List<CommodityEntity> newData) {
        commodityEntities = newData;
        notifyDataSetChanged();//通知更新
    }
    public ShoppingCartAdapter(List<CommodityEntity> commodityEntities) {
        this.commodityEntities = commodityEntities;
    }
    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.binding.textFood.setText(commodityEntities.get(position).getGenreC() + commodityEntities.get(position).getName() + "數量 : " + commodityEntities.get(position).getCount());
        Glide.with(context).load(commodityEntities.get(position).getImage()).into(holder.binding.imageFood);
    }

    @Override
    public int getItemCount() {
        return commodityEntities.size();
    }

    public class CartVH extends RecyclerView.ViewHolder {
        private CartItemBinding binding;
        public CartVH(@NonNull View itemView) {
            super(itemView);
            binding=CartItemBinding.bind(itemView);
        }
    }
}
