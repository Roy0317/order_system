package com.example.order_system;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    public List<Commodity2> jsonget(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("generated.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                stringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String str = stringBuilder.toString();
        Gson gson = new Gson();
        Type data = new TypeToken<ArrayList<Commodity2>>() {}.getType();
        return gson.fromJson(str, data);
    }
}
