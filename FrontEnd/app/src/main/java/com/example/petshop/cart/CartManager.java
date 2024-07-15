package com.example.petshop.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.example.petshop.Products.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<CartItem> cartItems;
    private SharedPreferences sharedPreferences;
    private static final String CART_PREFERENCES = "CartPreferences";

    private CartManager(Context context) {
        cartItems = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        loadCartFromSharedPreferences();

        // Xóa dữ liệu sau 5 phút
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> clearCart(), 5 * 60 * 1000); // 5 phút
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                saveCartToSharedPreferences();
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
        saveCartToSharedPreferences();
    }

    private void saveCartToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString("cart", json);
        editor.apply();
    }

    private void loadCartFromSharedPreferences() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cart", null);
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        if (json != null) {
            cartItems = gson.fromJson(json, type);
        }
    }

    public void clearCart() {
        cartItems.clear();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("cart");
        editor.apply();
    }
}
