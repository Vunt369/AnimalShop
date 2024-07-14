package com.example.petshop.Products;

import com.example.petshop.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("Products/get-all-product")
    Call<List<Product>> getProducts();

    @GET("Products/{id}")
    Call<ProductDetail> getProduct(@Path("id") int productId);
}
