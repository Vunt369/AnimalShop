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
        // Assuming cart is passed as an ArrayList<String>
        ArrayList<String> cart = getIntent().getStringArrayListExtra("cart");
        if(cart!=null){
            for (String item : cart) {
                int imageResource = getImageResourceForProduct(item);
                int productPrice = getProductPrice(item);
                cartItems.add(new CartItem(item, 1, imageResource, productPrice));
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
    private int getProductPrice(String productName){
        switch (productName) {
            case "Yếm cổ đáng yêu":
                return 35000;
            case "Thức ăn hạt khô":
                return 230000;
            case "Sữa tắm JOYCR&DOLCE":
                return 175000;
            case "Bát ăn nghiêng":
                return 45000;
            case "Bánh thưởng CATNIP":
                return 30000;
            case "Vòng cổ kèm chuông":
                return 35000;
            case "Balô cho mèo":
                return 200000;
            case "Bộ đồ chơi cho mèo":
                return 200000;
            default:
                return 0; // Default price if product name is not found
        }
    }
    private int calculateTotalCost(ArrayList<CartItem> cartItems) {
        int totalCost = 0;
        for (CartItem item : cartItems) {
            totalCost += item.getProductPrice() * item.getQuantity();
        }
        return totalCost;
    }
    private int getImageResourceForProduct(String productName) {
        switch (productName) {
            case "Yếm cổ đáng yêu":
                return R.drawable.yem_co;
            case "Thức ăn hạt khô":
                return R.drawable.thuc_an_hat_kho;
            case "Sữa tắm JOYCR&DOLCE":
                return R.drawable.sua_tam_joyce;
            case "Bát ăn nghiêng":
                return R.drawable.bat_an_nghieng;
            case "Bánh thưởng CATNIP":
                return R.drawable.banh_thuong_catnipo;
            case "Vòng cổ kèm chuông":
                return R.drawable.vong_co_kem_chuong;
            case "Balô cho mèo":
                return R.drawable.balo_cho_meo;
            case "Bộ đồ chơi cho mèo":
                return R.drawable.bo_do_choi_cho_meo;
            default:
                // Default image resource if product name doesn't match known cases
                return R.drawable.chan_meo_tach_nen;
        }
    }

}
