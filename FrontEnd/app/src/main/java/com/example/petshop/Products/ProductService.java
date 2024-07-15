package com.example.petshop.Products;

import com.example.petshop.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("Products/get-all-product")
    Call<List<Product>> getProducts();

    @GET("Products/{id}")
    Call<ProductDetail> getProduct(@Path("id") int productId);

    @GET("Products/search-products")
    Call<List<Product>> searchProducts(@Query("keywords") String keywords);

    @GET("Products/get-all-product")
    Call<List<Product>> getProductsByCategory(@Query("categoryId") int categoryId);

}
