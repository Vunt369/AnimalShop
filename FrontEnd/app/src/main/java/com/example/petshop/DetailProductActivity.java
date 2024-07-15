package com.example.petshop;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petshop.Products.ProductDetail;
import com.example.petshop.Products.ProductRepository;
import com.example.petshop.Products.ProductService;
import com.example.petshop.cart.CartActivity;
import com.example.petshop.cart.CartManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView imgProduct, imgCart;
    private TextView txtName, txtPrice,  txtDescription;
    private EditText edInputQuantity;
    private Button btnAddToCart;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);

        imgProduct = findViewById(R.id.img_product);
        imgCart = findViewById(R.id.img_cart);
        txtName = findViewById(R.id.txt_nameProduct);
        txtPrice = findViewById(R.id.txt_price);
        txtDescription = findViewById(R.id.textView12);
        edInputQuantity = findViewById(R.id.ed_input_soluong);
        btnAddToCart = findViewById(R.id.button);


        int productId = getIntent().getIntExtra("PRODUCT_ID", 1);
        fetchProductDetails(productId);
        btnAddToCart.setOnClickListener(v -> {
            String quantityText = edInputQuantity.getText().toString();
            if (quantityText.isEmpty()) {
                Toast.makeText(DetailProductActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            if (quantity < 1 || quantity > 50) {
                Toast.makeText(DetailProductActivity.this, "Quantity must be between 1 and 50", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save the product details to be passed to CartActivity without starting the CartActivity
            Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
            intent.putExtra("PRODUCT_ID", productId);
            intent.putExtra("PRODUCT_NAME", txtName.getText().toString());
            intent.putExtra("PRODUCT_PRICE", Integer.parseInt(txtPrice.getText().toString()));
            intent.putExtra("PRODUCT_QUANTITY", quantity);

            // Show notification that the product has been added to the cart
            String productName = txtName.getText().toString();
            Toast.makeText(DetailProductActivity.this, productName + " has been added to the cart", Toast.LENGTH_SHORT).show();

            // Store the intent for later use
            CartManager.setIntent(intent);

            // Optionally, handle transitioning to CartActivity using imgCart
            imgCart.setOnClickListener(view -> {
                startActivity(intent);
            });
        });
    }

    private void fetchProductDetails(int productId) {
        ProductService productService = ProductRepository.getProductService();
        Call<ProductDetail> call = productService.getProduct(productId);

        call.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetail productDetail = response.body();
                    displayProductDetails(productDetail);
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void displayProductDetails(ProductDetail productDetail) {
        txtName.setText(productDetail.getPname());
        txtPrice.setText(String.valueOf(productDetail.getPrice()));
        txtDescription.setText(productDetail.getDescription());
        Glide.with(this).load(productDetail.getImageUrl()).into(imgProduct);
    }

}
