package com.example.petshop.product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckoutService {

    @POST("Orders")
    Call<CheckoutReponse> createOrder(@Body CheckoutRequest orderRequest);

}
