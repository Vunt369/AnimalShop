package com.example.petshop.Products;

import com.example.petshop.User.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("Products")
    Call<Product[]> getProducts();
}
