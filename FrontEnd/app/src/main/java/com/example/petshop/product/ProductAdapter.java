package com.example.petshop.product;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petshop.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_checkout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView2);
        TextView motaSanpham = convertView.findViewById(R.id.motaSanpham);
        TextView giaSanpham = convertView.findViewById(R.id.giaSanpham);

        // Set image and text for each item
        imageView.setImageResource(product.getImageResId());
        motaSanpham.setText(product.getDescription());
        giaSanpham.setText(product.getPrice());




        return convertView;
    }
}