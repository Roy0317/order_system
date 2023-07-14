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

public class DessertFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;
    private List<Commodity2> commodity2;
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

        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getActivity().getApplication());

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        String savedTart = sharedPreferences.getString("TART_KEY", null);
        String savedIce = sharedPreferences.getString("ICE_KEY", null);

        if (savedTart != null) {
            createFoodFragment(commodity2.get(4),R.id.frameLayout15);
        }
        if (savedIce != null) {
            createFoodFragment(commodity2.get(5),R.id.frameLayout16);
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
                if (commodity2.get(4).type.equals(intent.getStringExtra("tart"))) {
                    createFoodFragment(commodity2.get(4), R.id.frameLayout15);
                    text(commodity2.get(4));
                }
            }
            if ("com.example.ACTION_ICE".equals(intent.getAction())) {
                if (commodity2.get(5).type.equals(intent.getStringExtra("ice"))) {
                    createFoodFragment(commodity2.get(5), R.id.frameLayout16);
                    text(commodity2.get(5));
                }
            }
        }
    };
    private void createFoodFragment(Commodity2 commodity2, int frameLayoutId) {
        // 創建對應的 Fragment
        Fragment fragment = null;
        if(commodity2.getType().equals("tart")){
            fragment = new Tart();
        } else if (commodity2.getType().equals("ice")) {
            fragment = new Ice();
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
        TextView textView = getView().findViewById(R.id.textView4);
        textView.setVisibility(View.GONE);
    }
}
