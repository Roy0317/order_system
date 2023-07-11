package com.example.order_system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SideDishFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.side_dish_fragment, container, false);
        TextView textView=view.findViewById(R.id.textView3);
        String savedOnion = sharedPreferences.getString("ONION_KEY", null);
        String savedDonut = sharedPreferences.getString("DONUT_KEY", null);
        if(savedOnion !=null){
            textView.setVisibility(View.GONE);
        }else if(savedDonut !=null){
            textView.setVisibility(View.GONE);
        }
            return view;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        String savedOnion = sharedPreferences.getString("ONION_KEY", null);
        String savedDonut = sharedPreferences.getString("DONUT_KEY", null);

        if (savedOnion != null) {
            SideFood sideFood=new SideFood("onion",true);
            createFoodFramgnet1(sideFood, R.id.frameLayout13);
        }
        if (savedDonut != null) {
            SideFood sideFood=new SideFood("donut",true);
            createFoodFramgnet1(sideFood, R.id.frameLayout14);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.ACTION_ONION");
        IntentFilter filter2 = new IntentFilter("com.example.ACTION_DONUT");
        getContext().registerReceiver(receiver, filter);
        getContext().registerReceiver(receiver, filter2);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.ACTION_ONION".equals(intent.getAction())) {
                String onions = intent.getStringExtra("onion");
                if (onions.equals("onion")) {
                    SideFood sideFood=new SideFood("onion",true);
                    createFoodFragment(sideFood, R.id.frameLayout13);
                }
            }
            if ("com.example.ACTION_DONUT".equals(intent.getAction())) {
                String donuts = intent.getStringExtra("donut");
                if (donuts.equals("donut")) {
                    SideFood sideFood=new SideFood("donut",true);
                    createFoodFragment(sideFood, R.id.frameLayout14);
                }
            }
        }
    };
    private void createFoodFragment(Commodity food, int frameLayoutId) {
        // 創建對應的 Fragment
        Fragment fragment = null;
        if(food.getName().equals("onion")){
            fragment = new Onion();
        } else if (food.getName().equals("donut")) {
            fragment = new Donut();
        }
        // 保存食物數據到 SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(food.getName().toUpperCase() + "_KEY", food.getName());
        editor.apply();
        // 動態添加 Fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutId, fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

        TextView textView = getView().findViewById(R.id.textView3);
        textView.setVisibility(View.GONE);
    }
    private void createFoodFramgnet1(Commodity food,int frameLayoutId) {
        Fragment fragment = null;
        if(food.getName().equals("onion")){
            fragment = new Onion();
        } else if (food.getName().equals("donut")) {
            fragment = new Donut();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutId, fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}

