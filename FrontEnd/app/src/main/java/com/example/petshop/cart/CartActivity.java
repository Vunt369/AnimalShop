package com.example.petshop.cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.Products.Product;
import com.example.petshop.Products.ProductAdapter;
import com.example.petshop.R;
import com.example.petshop.checkout.CheckoutActivity;
import com.example.petshop.product.ProductCheckout;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private Button checkoutButton;
    private TextView totalCostTextView;
    private ArrayList<Product> productsList;
    private ProductAdapter adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        checkoutButton = findViewById(R.id.checkoutButton);
        totalCostTextView = findViewById(R.id.totalCostTextView);
        cartItems = new ArrayList<>();

        // Initialize productsList and adapterProduct
        productsList = new ArrayList<>();
        adapterProduct = new ProductAdapter(productsList, this);

        // Assuming cart is passed as an ArrayList<Product>
        Intent intent = getIntent();
        ArrayList<Product> cart = (ArrayList<Product>) intent.getSerializableExtra("cart");
        if (cart != null) {
            for (Product product : cart) {
                cartItems.add(new CartItem(product, 1));
                productsList.add(product);  // Add products to productsList
            }
        }

        cartAdapter = new CartAdapter(this, cartItems, this::updateTotalCost);
        cartListView.setAdapter(cartAdapter);
        updateTotalCost();

        checkoutButton.setOnClickListener(v -> {
            ArrayList<ProductCheckout> selectedProducts = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                selectedProducts.add(new ProductCheckout(
                        cartItem.getProduct().getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()
                ));
            }

            Intent intentCheckout = new Intent(CartActivity.this, CheckoutActivity.class);
            intentCheckout.putExtra("checkout", selectedProducts);
            startActivity(intentCheckout);
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
