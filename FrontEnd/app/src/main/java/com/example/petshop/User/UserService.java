package com.example.petshop.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    String USER = "api/Users";

    @GET(USER)
    Call<User[]> getAllUsers();

    @GET(USER + "/{id}")
    Call<User> getUser(@Path("id") Object id);

    @POST("Users/sign-up")
    Call<User> signUp(@Body User user);

    @POST("Users/login")
    Call<User> login(@Body LoginRequest loginRequest);
}
