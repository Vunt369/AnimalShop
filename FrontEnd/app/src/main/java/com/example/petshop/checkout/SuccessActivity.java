package com.example.petshop.checkout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.HomePageActivity;
import com.example.petshop.R;

public class SuccessActivity extends AppCompatActivity {
    private Button buttonGoHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_success);

        Intent intent = getIntent();
        String payosUrl = intent.getStringExtra("payosUrl");

        if (payosUrl != null && !payosUrl.isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(payosUrl));
            startActivity(browserIntent);
        }
        buttonGoHome = findViewById(R.id.buttonGoHome);
        buttonGoHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(SuccessActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        });
    }
}
