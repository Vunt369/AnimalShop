package com.example.petshop.helper;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.StringReader;

public class JsonHelper {
    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        reader.setLenient(true); // Chấp nhận JSON không chuẩn

        try {
            return gson.fromJson(String.valueOf(reader), classOfT);
        } catch (JsonSyntaxException e) {
            // Xử lý lỗi phân tích cú pháp JSON
            e.printStackTrace();
            return null;
        }
    }
}
