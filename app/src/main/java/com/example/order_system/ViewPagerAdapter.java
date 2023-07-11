package com.example.order_system;

import static java.security.AccessController.getContext;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 檢查片段是否已存在於表中
        Fragment fragment = fragmentMap.get(position);
        if (fragment == null) {
            // 片段不存在創建新實例
            if (position == 0) {
                fragment = new MainCourseFragment();
            } else if (position == 1) {
                fragment = new SideDishFragment();
            } else if (position == 2) {
                fragment = new DessertFragment();
            }

            // 將片段保存到表中
            fragmentMap.put(position, fragment);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}
