package com.example.order_system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class MainCourseFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_course_fragment, container, false);
        TextView textView=view.findViewById(R.id.textView2);
        String savedPotato = sharedPreferences.getString("POTATO_KEY", null);
        String savedChicken = sharedPreferences.getString("CHICKEN_KEY", null);
        if(savedPotato!=null){
            textView.setVisibility(View.GONE);
        }else if(savedChicken!=null){
            textView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        String savedPotato = sharedPreferences.getString("POTATO_KEY", null);
        String savedChicken = sharedPreferences.getString("CHICKEN_KEY", null);

        if (savedPotato != null) {
            MainFood mainFood=new MainFood("potato",true);
            createFoodFragment1(mainFood, R.id.frameLayout11);
        }
        if (savedChicken != null) {
            MainFood mainFood=new MainFood("chicken",true);
            createFoodFragment1(mainFood, R.id.frameLayout12);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.ACTION_POTATO");
        IntentFilter filter2 = new IntentFilter("com.example.ACTION_CHICKEN");
        getContext().registerReceiver(receiver, filter);
        getContext().registerReceiver(receiver, filter2);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.ACTION_POTATO".equals(intent.getAction())) {
                String potatos = intent.getStringExtra("potato");
                if (potatos.equals("potato")) {
                    MainFood mainFood=new MainFood("potato",true);
                    createFoodFragment(mainFood, R.id.frameLayout11);
                }
            }
            if ("com.example.ACTION_CHICKEN".equals(intent.getAction())) {
                String chickens = intent.getStringExtra("chicken");
                if (chickens.equals("chicken")) {
                    MainFood mainFood=new MainFood("chicken",true);
                    createFoodFragment(mainFood, R.id.frameLayout12);
                }
            }
        }
    };
    private void createFoodFragment(Commodity food, int frameLayoutId) {
            // 創建對應的 Fragment
            Fragment fragment = null;
            if(food.getName().equals("potato")){
                fragment = new Potato();
            } else if (food.getName().equals("chicken")) {
                fragment = new Chicken();
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

            TextView textView = getView().findViewById(R.id.textView2);
            textView.setVisibility(View.GONE);
    }
    private void createFoodFragment1(Commodity food,int frameLayoutId) {
        Fragment fragment = null;
        if(food.getName().equals("potato")){
            fragment = new Potato();
        } else if (food.getName().equals("chicken")) {
            fragment = new Chicken();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutId, fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
