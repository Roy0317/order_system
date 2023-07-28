package com.example.order_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.order_system.databinding.ActivityMainFragment1Binding;
import com.example.order_system.databinding.FragmentMainCourseBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivityFragment1 extends Fragment {
    private ViewPagerAdapter adapter;
    private ActivityMainFragment1Binding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ActivityMainFragment1Binding.inflate(inflater,container,false);

        adapter = new ViewPagerAdapter(getActivity());
        binding.viewpager.setOffscreenPageLimit(3);
        binding.viewpager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tablayout, binding.viewpager,
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

        return binding.getRoot();
    }
}
