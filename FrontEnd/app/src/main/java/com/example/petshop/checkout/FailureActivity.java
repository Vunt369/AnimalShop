package com.example.petshop.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petshop.HomePageActivity;
import com.example.petshop.R;

public class FailureActivity extends AppCompatActivity {

    private Button buttonGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_failed);

        buttonGoHome = findViewById(R.id.buttonGoHome);
        buttonGoHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(FailureActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        });
    }
}
