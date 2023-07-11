package com.example.order_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Ice extends Fragment {
    private  static final String COUNTNAME="clickname";
    private static final String COUNTKEY="count6";
    private int clickCount = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ice, container, false);
        TextView textView=view.findViewById(R.id.text17);

        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences(COUNTNAME, Context.MODE_PRIVATE);
        clickCount=sharedPreferences2.getInt(COUNTKEY,0);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                String ice="甜筒";
                Intent intent = new Intent("com.example.order_system.POTATO_SELECTED6");
                intent.putExtra("ice", ice);
                intent.putExtra("count6", clickCount);


                Context context = requireContext();
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                SharedPreferences.Editor editor=sharedPreferences2.edit();
                editor.putInt(COUNTKEY,clickCount);
                editor.apply();
            }
        });
        return view;
    }
}
