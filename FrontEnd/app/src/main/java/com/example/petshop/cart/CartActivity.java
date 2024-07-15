package com.example.petshop.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.HomePageActivity;
import com.example.petshop.Products.Product;
import com.example.petshop.Products.ProductAdapter;
import com.example.petshop.R;
import com.example.petshop.checkout.CheckoutActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private Button checkoutButton, backButton;
    private TextView totalCostTextView;
    private ArrayList<CartItem> cartItems;
    private ArrayList<Product> productList;
    private ProductAdapter productAdapter;
    private SharedPreferences sharedPreferences;
    private static final String CART_PREFERENCES = "CartPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sharedPreferences = getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        cartListView = findViewById(R.id.cartListView);
        checkoutButton = findViewById(R.id.checkoutButton);
        backButton = findViewById(R.id.backToMainButton);
        totalCostTextView = findViewById(R.id.totalCostTextView);


        ArrayList<CartItem> cartItems = CartManager.getInstance(this).getCartItems();

        cartAdapter = new CartAdapter(this, cartItems, this::updateTotalCost);
        cartListView.setAdapter(cartAdapter);
        updateTotalCost();

        checkoutButton.setOnClickListener(v -> {
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                selectedProducts.add(cartItem.getProduct());
            }
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("checkout", selectedProducts);
            startActivity(intent);
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateTotalCost() {
        int totalCost = calculateTotalCost(CartManager.getInstance(this).getCartItems());
        totalCostTextView.setText("Tổng giá tiền: " + totalCost + " VND");
    }

    private int calculateTotalCost(ArrayList<CartItem> cartItems) {
        int totalCost = 0;
        for (CartItem item : cartItems) {
            totalCost += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalCost;
    }
}
