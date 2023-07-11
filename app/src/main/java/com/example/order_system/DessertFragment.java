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

public class DessertFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.dessert_fragment,container,false);
       TextView textView=view.findViewById(R.id.textView4);
        String savedTart = sharedPreferences.getString("TART_KEY", null);
        String savedIce = sharedPreferences.getString("ICE_KEY", null);
        if(savedTart!=null){
            textView.setVisibility(View.GONE);
        }else if(savedIce!=null){
            textView.setVisibility(View.GONE);
        }
       return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        String savedTart = sharedPreferences.getString("TART_KEY", null);
        String savedIce = sharedPreferences.getString("ICE_KEY", null);

        if (savedTart != null) {
            // 創建 Tart Fragment
            Dessert dessert=new Dessert("tart",true);
            createFoodFragment1(dessert, R.id.frameLayout15);
        }
        if (savedIce != null) {
            // 創建 Ice Fragment
            Dessert dessert=new Dessert("ice",true);
            createFoodFragment1(dessert, R.id.frameLayout16);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.ACTION_TART");
        IntentFilter filter2 = new IntentFilter("com.example.ACTION_ICE");
        getContext().registerReceiver(receiver, filter);
        getContext().registerReceiver(receiver, filter2);
    }



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.ACTION_TART".equals(intent.getAction())) {
                String tarts = intent.getStringExtra("tart");
                if (tarts.equals("tart")) {
                    Dessert dessert=new Dessert("tart",true);
                    createFoodFragment(dessert, R.id.frameLayout15);
                }
            }
            if ("com.example.ACTION_ICE".equals(intent.getAction())) {
                String ices = intent.getStringExtra("ice");
                if (ices.equals("ice")) {
                    Dessert dessert=new Dessert("ice",true);
                    createFoodFragment(dessert, R.id.frameLayout16);
                }
            }
        }
    };
    private void createFoodFragment(Commodity food, int frameLayoutId) {
        // 創建對應的 Fragment
        Fragment fragment = null;
        if(food.getName().equals("tart")){
            fragment = new Tart();
        } else if (food.getName().equals("ice")) {
            fragment = new Ice();
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

        TextView textView = getView().findViewById(R.id.textView4);
        textView.setVisibility(View.GONE);
    }
    private void createFoodFragment1(Commodity food,int frameLayoutId) {
        Fragment fragment = null;
        if(food.getName().equals("tart")){
            fragment = new Tart();
        } else if (food.getName().equals("ice")) {
            fragment = new Ice();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutId, fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
