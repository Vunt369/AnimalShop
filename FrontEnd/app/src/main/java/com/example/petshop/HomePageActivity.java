package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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

import com.example.petshop.cart.CartActivity;

import com.example.petshop.categories.Category;
import com.example.petshop.categories.CategoryAdapter;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    ArrayList<Category> categoriesList;
    ArrayList<Product> productsList;
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
                intent.putStringArrayListExtra("cart", adapterProduct.getCartItems());
                startActivity(intent);
            }
        });
        setupCategories();
        setupProducts();

        ImageButton startChatButton = findViewById(R.id.chatBubble);
        startChatButton.setOnClickListener(v -> chatNow());

    }

    private void chatNow() {
        Intent intent = new Intent(HomePageActivity.this, MessageActivity.class);
        intent.putExtra("CURRENT_USER_ID", 1); // Replace with actual user ID
        intent.putExtra("CURRENT_USERNAME", "User1"); // Replace with actual username
        intent.putExtra("CURRENT_CONTACT", "TUNGLD"); // Replace with actual contact
        startActivity(intent);
    }

    private void setupCategories() {
        RecyclerView rvCategories = findViewById(R.id.recycle_menu_categories);

        categoriesList = new ArrayList<>();
        categoriesList.add(new Category(R.drawable.dochoi_thucung));
        categoriesList.add(new Category(R.drawable.thucan_cun));
        categoriesList.add(new Category(R.drawable.thucan_meo));
        categoriesList.add(new Category(R.drawable.chau_cat_vesinh));

        CategoryAdapter adapter = new CategoryAdapter(categoriesList);
        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupProducts() {
        RecyclerView rvProducts = findViewById(R.id.recycler_products);
//        productsList = new ArrayList<>();
//        productsList.add(new Product(R.drawable.yem_co, "Yếm cổ đáng yêu", 35000));
//        productsList.add(new Product(R.drawable.thuc_an_hat_kho, "Thức ăn hạt khô", 230000));
//        productsList.add(new Product(R.drawable.sua_tam_joyce, "Sữa tắm JOYCR&DOLCE", 175000));
//        productsList.add(new Product(R.drawable.bat_an_nghieng, "Bát ăn nghiêng", 45000));
//        productsList.add(new Product(R.drawable.banh_thuong_catnipo, "Bánh thưởng CATNIP", 30000));
//        productsList.add(new Product(R.drawable.vong_co_kem_chuong, "Vòng cổ kèm chuông", 35000));
//        productsList.add(new Product(R.drawable.balo_cho_meo, "Balô cho mèo", 200000));
//        productsList.add(new Product(R.drawable.bo_do_choi_cho_meo, "Bộ đồ chơi cho mèo", 200000));

        adapterProduct = new ProductAdapter(productsList, this);
        rvProducts.setAdapter(adapterProduct);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
    }

}
