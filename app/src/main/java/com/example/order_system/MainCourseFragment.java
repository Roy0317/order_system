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

import java.util.List;

public class MainCourseFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "FragmentData";
    private SharedPreferences sharedPreferences;
    private List<Commodity2> commodity2;

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

        JsonHelper jsonHelper = new JsonHelper();
        commodity2 = jsonHelper.jsonget(getActivity().getApplication());

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        String savedPotato = sharedPreferences.getString("POTATO_KEY", null);
        String savedChicken = sharedPreferences.getString("CHICKEN_KEY", null);

        if (savedPotato != null) {
            createFoodFragment(commodity2.get(0), R.id.frameLayout11);
        }
        if (savedChicken != null) {
            createFoodFragment(commodity2.get(1), R.id.frameLayout12);
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
                if (commodity2.get(0).type.equals(intent.getStringExtra("potato"))) {
                    createFoodFragment(commodity2.get(0), R.id.frameLayout11);
                    text(commodity2.get(0));
                }
            }
            if ("com.example.ACTION_CHICKEN".equals(intent.getAction())) {
                if (commodity2.get(1).type.equals(intent.getStringExtra("chicken"))) {
                    createFoodFragment(commodity2.get(1), R.id.frameLayout12);
                    text(commodity2.get(1));
                }
            }
        }
    };
    private void createFoodFragment(Commodity2 commodity2, int frameLayoutId) {
            // 創建對應的 Fragment
            Fragment fragment = null;
            if(commodity2.type.equals("potato")){
                fragment = new Potato();
            } else if (commodity2.type.equals("chicken")) {
                fragment = new Chicken();
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
        TextView textView = getView().findViewById(R.id.textView2);
        textView.setVisibility(View.GONE);
    }
}
