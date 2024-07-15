package com.example.petshop.cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.Products.Product;
import com.example.petshop.Products.ProductAdapter;
import com.example.petshop.Products.ProductDetail;
import com.example.petshop.R;
import com.example.petshop.checkout.CheckoutActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private Button checkoutButton;
    private TextView totalCostTextView;
    private ArrayList<Product> productsList;  // Changed to ArrayList to match the initialization
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

        // Handle intent from DetailProductActivity
        int productId = intent.getIntExtra("PRODUCT_ID", -1);
        String productName = intent.getStringExtra("PRODUCT_NAME");
        int productPrice = intent.getIntExtra("PRODUCT_PRICE", -1);
        int productQuantity = intent.getIntExtra("PRODUCT_QUANTITY", -1);

        if (productId != -1 && productName != null && productPrice != -1 && productQuantity != -1) {
            Product product = new Product(productName, productPrice, "");
            cartItems.add(new CartItem(product, productQuantity));
            productsList.add(product);
        }

        cartAdapter = new CartAdapter(this, cartItems, this::updateTotalCost);
        cartListView.setAdapter(cartAdapter);
        updateTotalCost();

        checkoutButton.setOnClickListener(v -> {
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                selectedProducts.add(cartItem.getProduct());
            }
            Intent intent2 = new Intent(CartActivity.this, CheckoutActivity.class);
            intent2.putExtra("checkout", selectedProducts);
            startActivity(intent2);
        });
    }

    private void updateTotalCost() {
        int totalCost = calculateTotalCost(cartItems);
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
