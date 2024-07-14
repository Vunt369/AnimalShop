package com.example.petshop.checkout;

import android.annotation.SuppressLint;
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
import com.example.petshop.product.Product;
import com.example.petshop.product.ProductAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    private EditText etName, etPhone, etAddress;
    private RadioButton radioButtonCOD, radioButtonBankTransfer;
    private Button buttonCompleteOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ListView listView = findViewById(R.id.listView);
        TextView giaTamtinh = findViewById(R.id.giaTamtinh);
        TextView giaPhivanchuyen = findViewById(R.id.giaPhivanchuyen);
        TextView giaTongcong = findViewById(R.id.giaTongcong);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        radioButtonCOD = findViewById(R.id.radioButtonCOD);
        radioButtonBankTransfer = findViewById(R.id.radioButtonBankTransfer);
        buttonCompleteOrder = findViewById(R.id.buttonCompleteOrder);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.chau_cat_vesinh, "Chậu cát cho mèo", "1", "100000")); // Giả sử giá là số nguyên không có ký hiệu tiền tệ
        // Thêm nhiều sản phẩm khác nếu cần

        ProductAdapter adapter = new ProductAdapter(this, products);
        listView.setAdapter(adapter);

        // Tính tổng giá tiền
        int totalPrice = calculateTotalPrice(products);
        giaTamtinh.setText(totalPrice + " VND");

        // Set phí vận chuyển cố định
        int shippingFee = 30000;
        giaPhivanchuyen.setText(shippingFee + " VND");

        // Tính tổng cộng
        int finalTotal = totalPrice + shippingFee;
        giaTongcong.setText(finalTotal + " VND");

        // Xử lý khi nhấn nút hoàn tất
        buttonCompleteOrder.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (radioButtonCOD.isChecked()) {
                // Chuyển hướng sang màn hình khác khi chọn COD
                Intent intent = new Intent(CheckoutActivity.this, SuccessActivity.class);
                startActivity(intent);
            } else if (radioButtonBankTransfer.isChecked()) {
                // Xử lý logic cho thanh toán ngân hàng ở đây sau
                Toast.makeText(CheckoutActivity.this, "Thanh toán qua ngân hàng chưa được xử lý", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CheckoutActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int calculateTotalPrice(ArrayList<Product> products) {
        int totalPrice = 0;
        for (Product product : products) {
            int quantity = Integer.parseInt(product.getQuantity());
            String priceString = product.getPrice().replace(",", ""); // Loại bỏ dấu phẩy
            int price = Integer.parseInt(priceString);
            totalPrice += quantity * price;
        }
        return totalPrice;
    }
}
