package com.example.petshop.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petshop.R;
import com.example.petshop.checkout.SuccessActivity;
import com.example.petshop.product.Product;
import com.example.petshop.product.ProductAdapter;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etPhone, etAddress;
    private RadioButton radioButtonCOD, radioButtonBankTransfer;
    private Button buttonCompleteOrder;
    private TextView giaTamtinh, giaPhivanchuyen, giaTongcong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ListView listView = findViewById(R.id.listView);
        giaTamtinh = findViewById(R.id.giaTamtinh);
        giaPhivanchuyen = findViewById(R.id.giaPhivanchuyen);
        giaTongcong = findViewById(R.id.giaTongcong);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        radioButtonCOD = findViewById(R.id.radioButtonCOD);
        radioButtonBankTransfer = findViewById(R.id.radioButtonBankTransfer);
        buttonCompleteOrder = findViewById(R.id.buttonCompleteOrder);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.chau_cat_vesinh, "Chậu cát cho mèo", "1", "100000")); // Example product
        // Add more products if needed

        ProductAdapter adapter = new ProductAdapter(this, products);
        listView.setAdapter(adapter);

        // Calculate total price
        int totalPrice = calculateTotalPrice(products);
        giaTamtinh.setText(totalPrice + " VND");

        // Set fixed shipping fee
        int shippingFee = 30000;
        giaPhivanchuyen.setText(shippingFee + " VND");

        // Calculate final total
        int finalTotal = totalPrice + shippingFee;
        giaTongcong.setText(finalTotal + " VND");


        // Handle complete order button click
        buttonCompleteOrder.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                return;
            }

            if (radioButtonCOD.isChecked()) {
                // Redirect to success screen when choosing COD
                Intent intent = new Intent(CheckoutActivity.this, SuccessActivity.class);
                startActivity(intent);
            } else if (radioButtonBankTransfer.isChecked()) {
                // Handle logic for bank transfer payment here
                Toast.makeText(CheckoutActivity.this, "Bank transfer payment is not implemented yet", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CheckoutActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int calculateTotalPrice(ArrayList<Product> products) {
        int totalPrice = 0;
        for (Product product : products) {
            int quantity = Integer.parseInt(product.getQuantity());
            String priceString = product.getPrice().replace(",", ""); // Remove comma separators
            int price = Integer.parseInt(priceString);
            totalPrice += quantity * price;
        }
        return totalPrice;
    }
}
