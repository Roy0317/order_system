package com.example.order_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivityFragment1 extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment1, container, false);

        tabLayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getActivity());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // 在這裡設置每個 Tab 的標題
                    if (position == 0) {
                        tab.setText("主餐");
                    } else if (position == 1) {
                        tab.setText("附餐");
                    } else if (position == 2) {
                        tab.setText("點心");
                    }
                });
        tabLayoutMediator.attach();

        return view;
    }
}
