package com.example.order_system;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewFoodFragment extends Fragment {
    private List<Commodity2> foods;
    private String fragmentType;
    private List<String> foodName;
    private FoodAdapter foodAdapter;
    private CommodityUtil commodityUtil;
    public NewFoodFragment(List<Commodity2> allFoods, String type){
        this.foods=allFoods;
        this.fragmentType=type;
        this.foodName=new ArrayList<>();
    }
    //eventbus
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onFoodClicked(MainActivityFragment2.FoodClickedEvent event) {
        if (event != null && event.getFoodName() != null) {
            String foodNamelist = event.getFoodName();
            foodName.add(foodNamelist); // 将接收到的食物名称添加到列表中
            Log.d("Tgg", "onFoodClicked: "+foodName);
            commodityUtil.saveFoodName(foodName);     // 保存點擊過的,確保重啟也保留更新過的食物

            if (foodAdapter != null) {
                foodAdapter.updateFoodName(foodName); // 更新食物名称列表,每次點擊食物都會更新
            }

        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = R.layout.fragment_main_course;

        //讀取sharepreferences的資料
        commodityUtil = CommodityUtil.getInstance(requireContext());//初始化sharedpreferences
        List<String> savedFoodNames = commodityUtil.getFoodName(); // 讀取點擊過的食物
        foodName.addAll(savedFoodNames);

        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //json獲取資料
        foods = new JsonHelper().jsonget(getActivity().getApplication());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));

        List<Commodity2> filteredFoods = new ArrayList<>();
        for (Commodity2 food : foods) {
            if (food.getGenre().equals(fragmentType)) {
                filteredFoods.add(food);
            }
        }

        foodAdapter = new FoodAdapter(filteredFoods,foodName);
        recyclerView.setAdapter(foodAdapter);
    }



    public class FoodAdapter extends RecyclerView.Adapter<FoodVH> {
        private List<Commodity2> foods;
        private List<String> foodName;
        public FoodAdapter(List<Commodity2> f, List<String> foodName){
            this.foods = f;
            this.foodName = foodName;
        }
        public void updateFoodName(List<String> name) {
            this.foodName = name;
            notifyDataSetChanged();//通知更新
        }
        @Override
        public int getItemCount() {
            if(foods == null)
                return 0;
            else
                return foods.size();
        }

        @NonNull
        @Override
        public FoodVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_food, null);
            return new FoodVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodVH holder, int position) {
            holder.bindView(foods.get(position),foodName);
        }
    }
//顯示每一個食物
    public class FoodVH extends RecyclerView.ViewHolder{

        private ImageView imageFood;
        private TextView textFood;
        private TextView textCart;

        public FoodVH(View view){
            super(view);
            imageFood = view.findViewById(R.id.imageFood);
            textFood = view.findViewById(R.id.textFood);
            textCart = view.findViewById(R.id.textCart);
        }

        public void bindView(Commodity2 commodity2, List<String> foodName) {
            textCart.setOnClickListener(view -> onFoodClicked(commodity2));
            if (foodName == null || foodName.isEmpty() || !foodName.contains(commodity2.getType())) { // 檢察食物list是否為空
                imageFood.setImageResource(R.drawable.def);
                textFood.setText("");
                textCart.setText("");
                textCart.setBackgroundColor(Color.WHITE);
            } else {
                textCart.setText("放入購物車");
                textCart.setBackgroundColor(Color.GRAY);
                Glide.with(itemView.getContext()).load(commodity2.getImage()).into(imageFood);
                textFood.setText(commodity2.getName());
            }
        }
        //點擊事件
        private void onFoodClicked(Commodity2 commodity2){
            Bundle bundle = new Bundle();
            bundle.putString("food", commodity2.getType());
            bundle.putInt("count", commodity2.getCount());

            Context context = itemView.getContext();
            if (context instanceof MainActivity) {
                ((MainActivity) context).onFoodClick(bundle);
            }
        }
    }

}