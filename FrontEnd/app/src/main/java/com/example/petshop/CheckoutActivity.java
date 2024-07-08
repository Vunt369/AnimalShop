package com.example.petshop;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petshop.product.Product;
import com.example.petshop.product.ProductAdapter;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ListView listView = findViewById(R.id.listView);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.chau_cat_vesinh, "Chậu cát cho mèo", "100,000 VND"));
        // Add more products as needed

        ProductAdapter adapter = new ProductAdapter(this, products);
        listView.setAdapter(adapter);
    }
}
