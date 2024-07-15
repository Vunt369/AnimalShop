package com.example.petshop.product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckoutService {

    @POST("Orders/checkout-payos")
    Call<CheckoutReponse> createOrderPayos(@Body CheckoutRequest orderRequest);

    @POST("Orders/checkout-Cod")
    Call<CheckoutReponse> createOrderCod(@Body CheckoutRequest orderRequest);
}
