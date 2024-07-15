package com.example.petshop.checkout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petshop.R;

public class SuccessActivity extends AppCompatActivity {

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
    }
}
