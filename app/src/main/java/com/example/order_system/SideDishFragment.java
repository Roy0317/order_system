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

import java.util.List;

public class SideDishFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;
    private List<Commodity2> commodity2;
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

        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getActivity().getApplication());

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        String savedOnion = sharedPreferences.getString("ONION_KEY", null);
        String savedDonut = sharedPreferences.getString("DONUT_KEY", null);

        if (savedOnion != null) {
            createFoodFragment(commodity2.get(2), R.id.frameLayout13);
        }
        if (savedDonut != null) {
            createFoodFragment(commodity2.get(3), R.id.frameLayout14);
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
                if (commodity2.get(2).type.equals(intent.getStringExtra("onion"))) {
                    createFoodFragment(commodity2.get(2), R.id.frameLayout13);
                    text(commodity2.get(2));
                }
            }
            if ("com.example.ACTION_DONUT".equals(intent.getAction())) {
                if (commodity2.get(3).type.equals(intent.getStringExtra("donut"))) {
                    createFoodFragment(commodity2.get(3), R.id.frameLayout14);
                    text(commodity2.get(3));
                }
            }
        }
    };
    private void createFoodFragment(Commodity2 commodity2, int frameLayoutId) {
        // 創建對應的 Fragment
        Fragment fragment = null;
        if(commodity2.getType().equals("onion")){
            fragment = new Onion();
        } else if (commodity2.getType().equals("donut")) {
            fragment = new Donut();
        }

        // 動態添加 Fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutId, fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

    }
    private void text(Commodity2 commodity2){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(commodity2.type.toUpperCase() + "_KEY", commodity2.getType());
        editor.apply();
        TextView textView = getView().findViewById(R.id.textView3);
        textView.setVisibility(View.GONE);
    }
}

