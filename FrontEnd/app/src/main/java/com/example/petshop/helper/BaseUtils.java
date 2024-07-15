package com.example.petshop.helper;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class BaseUtils {
//    public static String API_URI = "http://157.66.24.101:5000/";
//    public static String API_URI = "http://157.66.24.101:80/";
    public static String API_URI = "http://192.168.1.6:5000/";

    public static Retrofit ApiServiceGI() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URI)
                .build();
    }
}
