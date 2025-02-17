package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.petshop.Products.Product;
import com.example.petshop.Products.ProductAdapter;

import com.example.petshop.Products.ProductRepository;
import com.example.petshop.Products.ProductService;
import com.example.petshop.User.UserClient;
import com.example.petshop.User.UserRepository;
import com.example.petshop.cart.CartActivity;

import com.example.petshop.categories.Category;
import com.example.petshop.categories.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {

    ArrayList<Category> categoriesList;
    ArrayList<Product> productsList;

    private RecyclerView rvProducts;
    private ProductAdapter adapterProduct;
    private EditText etSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest"); // Default to "Guest" if not found

        TextView txtNameUser = findViewById(R.id.txt_nameUser); // Make sure you have this TextView in your layout
        txtNameUser.setText(username);

        etSearch = findViewById(R.id.ed_search);
        ImageView imgSearch = findViewById(R.id.img_search);
        imgSearch.setOnClickListener(v -> performSearch());

        ImageView imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(v -> {
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (String productName : adapterProduct.getCartItems()) {
                for (Product product : productsList) {
                    if (product.getPname().equals(productName)) {
                        selectedProducts.add(product);
                        break;
                    }
                }
            }
            Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
            intent.putExtra("cart", selectedProducts);
            startActivity(intent);
        });

        RecyclerView rvCategories = findViewById(R.id.recycle_menu_categories);

        categoriesList = new ArrayList<>();
        categoriesList.add(new Category(R.drawable.dochoi_thucung));
        categoriesList.add(new Category(R.drawable.thucan_cun));
        categoriesList.add(new Category(R.drawable.thucan_meo));
        categoriesList.add(new Category(R.drawable.chau_cat_vesinh));

        CategoryAdapter adapter = new CategoryAdapter(categoriesList);
        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView rvProducts = findViewById(R.id.recycler_products);
        productsList = new ArrayList<>();

        adapterProduct = new ProductAdapter(productsList,this);

        rvProducts.setAdapter(adapterProduct);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        
        fetchProducts();


    }
    private void fetchProducts() {
        ProductService productApi = ProductRepository.getProductService();
        Call<List<Product>> call = productApi.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productsList.addAll(response.body());
                    adapterProduct.notifyDataSetChanged();
                }else {
                    Toast.makeText(HomePageActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch() {
        String keywords = etSearch.getText().toString().trim();
        searchProducts(keywords);
    }

    private void searchProducts(String keywords) {
        ProductService productApi = ProductRepository.getProductService();
        Call<List<Product>> call = productApi.searchProducts(keywords);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productsList.clear();
                    productsList.addAll(response.body());
                    adapterProduct.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomePageActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}