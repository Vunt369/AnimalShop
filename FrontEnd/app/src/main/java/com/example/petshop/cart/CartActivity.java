package com.example.petshop.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.Products.Product;
import com.example.petshop.R;
import com.example.petshop.checkout.CheckoutActivity;

import java.util.ArrayList;
public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private Button checkoutButton;
    private TextView totalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        cartListView = findViewById(R.id.cartListView);
        checkoutButton = findViewById(R.id.checkoutButton);
        totalCostTextView = findViewById(R.id.totalCostTextView);

        cartItems = new ArrayList<>();
        // Assuming cart is passed as an ArrayList<Product>
        ArrayList<Product> cart = (ArrayList<Product>) getIntent().getSerializableExtra("cart");
        if (cart != null) {
            for (Product product : cart) {
                cartItems.add(new CartItem(product, 1));
            }
        }


        cartAdapter = new CartAdapter(this, cartItems, this::updateTotalCost);
        cartListView.setAdapter(cartAdapter);
        updateTotalCost();


        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateTotalCost() {
        int totalCost = calculateTotalCost(cartItems);
        totalCostTextView.setText("Total Cost: $" + totalCost);
    }

    private int calculateTotalCost(ArrayList<CartItem> cartItems) {
        int totalCost = 0;
        for (CartItem item : cartItems) {
            totalCost += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalCost;
    }


}
