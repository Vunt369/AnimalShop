package com.example.petshop.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    private static String baseUrl = "https://petshopapiv2.azurewebsites.net/api/";
    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit ==  null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
