package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ImageView imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
                startActivity(intent);
            }
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

        adapterProduct = new ProductAdapter(productsList);
        rvProducts.setAdapter(adapterProduct);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
      /*  productsList.add(new Product(R.drawable.yem_co, "Yếm cổ đáng yêu", 35000));
        productsList.add(new Product(R.drawable.thuc_an_hat_kho, "Thức ăn hạt khô", 230000));
        productsList.add(new Product(R.drawable.sua_tam_joyce, "Sữa tắm JOYCR&DOLCE", 175000));
        productsList.add(new Product(R.drawable.bat_an_nghieng, "Bát ăn nghiêng", 45000));
        productsList.add(new Product(R.drawable.banh_thuong_catnipo, "Bánh thưởng CATNIP", 30000));
        productsList.add(new Product(R.drawable.vong_co_kem_chuong, "Vòng cổ kèm chuông", 35000));
        productsList.add(new Product(R.drawable.balo_cho_meo, "Balô cho mèo", 200000));
        productsList.add(new Product(R.drawable.bo_do_choi_cho_meo, "Bộ đồ chơi cho mèo", 200000));
*/


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
}
