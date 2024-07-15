package com.example.petshop.product;

import com.example.petshop.User.UserClient;

public class CheckoutRepository {
    public static CheckoutService getCheckoutService() {
        return UserClient.getClient().create(CheckoutService.class);
    }
}