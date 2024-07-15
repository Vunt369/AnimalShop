package com.example.petshop.cart;

import android.content.Intent;

import com.example.petshop.Products.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static Intent intent;

    public static Intent getIntent() {
        return intent;
    }

    public static void setIntent(Intent newIntent) {
        intent = newIntent;
    }
}
