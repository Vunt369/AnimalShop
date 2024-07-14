package com.example.petshop;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petshop.Products.ProductDetail;
import com.example.petshop.Products.ProductRepository;
import com.example.petshop.Products.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView imgProduct;
    private TextView txtName, txtPrice,  txtDescription;
    private EditText edInputQuantity;
    private Button btnAddToCart;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);

        imgProduct = findViewById(R.id.img_product);
        txtName = findViewById(R.id.txt_nameProduct);
        txtPrice = findViewById(R.id.txt_price);
        txtDescription = findViewById(R.id.textView12);
        edInputQuantity = findViewById(R.id.ed_input_soluong);
        btnAddToCart = findViewById(R.id.button);

        int productId = getIntent().getIntExtra("PRODUCT_ID", 1);
        fetchProductDetails(productId);
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
