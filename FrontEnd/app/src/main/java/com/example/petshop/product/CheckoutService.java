package com.example.petshop.product;

import com.example.petshop.checkout.OrderInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckoutService {

    @POST("api/Orders")
        // Sử dụng POST request để gửi thông tin đơn hàng
    Call<OrderInfo> placeOrder(@Body OrderInfo orderInfo);

}

