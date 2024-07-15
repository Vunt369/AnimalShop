package com.example.petshop.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.Products.Product;
import com.example.petshop.R;
import com.example.petshop.cart.CartActivity;
import com.example.petshop.product.CheckoutRepository;
import com.example.petshop.product.CheckoutRequest;
import com.example.petshop.product.CheckoutReponse;
import com.example.petshop.product.CheckoutService;
import com.example.petshop.product.ProductCheckout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etPhone, etAddress;
    private RadioButton radioButtonCOD, radioButtonBankTransfer;
    private RadioGroup radioGroupPaymentMethod;
    private Button buttonCompleteOrder, buttonBackToCart;
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
        radioGroupPaymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        buttonCompleteOrder = findViewById(R.id.buttonCompleteOrder);
        buttonBackToCart = findViewById(R.id.buttonBackToCart);

        Intent intent = getIntent();
        ArrayList<ProductCheckout> checkout = (ArrayList<ProductCheckout>) intent.getSerializableExtra("checkout");
        if (checkout != null) {
            int totalPrice = calculateTotalPrice(checkout);
            giaTamtinh.setText(totalPrice + " VND");

            int shippingFee = 30000;
            giaPhivanchuyen.setText(shippingFee + " VND");

            int finalTotal = totalPrice + shippingFee;
            giaTongcong.setText(finalTotal + " VND");
        }

        buttonCompleteOrder.setOnClickListener(v -> {
            String fullName = etName.getText().toString().trim();
            String phoneNumber = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number
            if (!isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(CheckoutActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPaymentMethodId = radioGroupPaymentMethod.getCheckedRadioButtonId();
            if (selectedPaymentMethodId == -1) {
                Toast.makeText(CheckoutActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedPaymentMethodId == R.id.radioButtonCOD) {
                makeOrderRequestCod(checkout, fullName, phoneNumber, address);
            } else if (selectedPaymentMethodId == R.id.radioButtonBankTransfer) {
                makeOrderRequestPayos(checkout, fullName, phoneNumber, address);
            }
        });

        buttonBackToCart.setOnClickListener(v -> {
            Intent intentBackToCart = new Intent(CheckoutActivity.this, CartActivity.class);
            startActivity(intentBackToCart);
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^0\\d{9}$");
    }

    private int calculateTotalPrice(ArrayList<ProductCheckout> products) {
        int totalPrice = 0;
        for (ProductCheckout product : products) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        return totalPrice;
    }

    private void makeOrderRequestCod(ArrayList<ProductCheckout> checkout, String fullName, String phoneNumber, String address) {
        // Create ShipmentDetailDTO with necessary parameters
        CheckoutRequest.ShipmentDetailDTO shipmentDetailDTO = new CheckoutRequest.ShipmentDetailDTO(
                fullName,
                address,
                phoneNumber
        );

        // Create list of OrderDetail
        List<ProductCheckout> orderDetails = new ArrayList<>();
        for (ProductCheckout product : checkout) {
            ProductCheckout detail = new ProductCheckout(
                    product.getProductId(),
                    product.getPrice(),
                    product.getQuantity()
            );
            orderDetails.add(detail);
        }

        // Create CheckoutRequest with all parameters
        CheckoutRequest orderRequest = new CheckoutRequest(
                1, // userId
                calculateTotalPrice(checkout), // intoMoney
                calculateTotalPrice(checkout) + 30000, // totalPrice, including transport fee
                30000, // tranSportFee
                shipmentDetailDTO, // shipmentDetailDTO
                orderDetails // orderDetailList
        );

        CheckoutService service = CheckoutRepository.getCheckoutService();
        Call<CheckoutReponse> call = service.createOrderCod(orderRequest);
        call.enqueue(new Callback<CheckoutReponse>() {
            @Override
            public void onResponse(Call<CheckoutReponse> call, Response<CheckoutReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CheckoutActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                    Intent intentSuccess = new Intent(CheckoutActivity.this, SuccessActivity.class);
                    startActivity(intentSuccess);
                } else {
                    String errorMessage = "Order creation failed";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(CheckoutActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    Intent intentFailure = new Intent(CheckoutActivity.this, FailureActivity.class);
                    startActivity(intentFailure);
                }
            }

            @Override
            public void onFailure(Call<CheckoutReponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
                Intent intentFailure = new Intent(CheckoutActivity.this, FailureActivity.class);
                startActivity(intentFailure);
            }
        });
    }

    private void makeOrderRequestPayos(ArrayList<ProductCheckout> checkout, String fullName, String phoneNumber, String address) {
        // Create ShipmentDetailDTO with necessary parameters
        CheckoutRequest.ShipmentDetailDTO shipmentDetailDTO = new CheckoutRequest.ShipmentDetailDTO(
                fullName,
                address,
                phoneNumber
        );

        // Create list of OrderDetail
        List<ProductCheckout> orderDetails = new ArrayList<>();
        for (ProductCheckout product : checkout) {
            ProductCheckout detail = new ProductCheckout(
                    product.getProductId(),
                    product.getPrice(),
                    product.getQuantity()
            );
            orderDetails.add(detail);
        }

        // Create CheckoutRequest with all parameters
        CheckoutRequest orderRequest = new CheckoutRequest(
                1, // userId
                calculateTotalPrice(checkout), // intoMoney
                calculateTotalPrice(checkout) + 30000, // totalPrice, including transport fee
                30000, // tranSportFee
                shipmentDetailDTO, // shipmentDetailDTO
                orderDetails // orderDetailList
        );

        CheckoutService service = CheckoutRepository.getCheckoutService();
        Call<CheckoutReponse> call = service.createOrderPayos(orderRequest);
        call.enqueue(new Callback<CheckoutReponse>() {
            @Override
            public void onResponse(Call<CheckoutReponse> call, Response<CheckoutReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String payosUrl = response.body().getData();
                    Intent intentPayos = new Intent(CheckoutActivity.this, SuccessActivity.class);
                    intentPayos.putExtra("payosUrl", payosUrl);
                    startActivity(intentPayos);
                } else {
                    String errorMessage = "Order creation failed";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(CheckoutActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    Intent intentFailure = new Intent(CheckoutActivity.this, FailureActivity.class);
                    startActivity(intentFailure);
                }
            }

            @Override
            public void onFailure(Call<CheckoutReponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
                Intent intentFailure = new Intent(CheckoutActivity.this, FailureActivity.class);
                startActivity(intentFailure);
            }
        });
    }
}
