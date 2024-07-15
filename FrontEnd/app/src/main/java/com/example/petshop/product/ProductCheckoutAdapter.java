package com.example.petshop.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petshop.R;

import java.util.List;

public class ProductCheckoutAdapter extends BaseAdapter {
    private Context context;
    private List<ProductCheckout> productCheckoutList;
    private LayoutInflater inflater;

    public ProductCheckoutAdapter(Context context, List<ProductCheckout> productCheckoutList) {
        this.context = context;
        this.productCheckoutList = productCheckoutList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productCheckoutList.size();
    }

    @Override
    public Object getItem(int position) {
        return productCheckoutList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_checkout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView2);
        TextView productDescription = convertView.findViewById(R.id.motaSanpham);
        TextView productQuantity = convertView.findViewById(R.id.soluong);
        TextView productPrice = convertView.findViewById(R.id.giaSanpham);

        ProductCheckout productCheckout = productCheckoutList.get(position);

        // Assuming you have a method to get image resource for the product
        // imageView.setImageResource(productCheckout.getImageResId());

        productDescription.setText(productCheckout.getProductId()); // Set appropriate value
        productQuantity.setText(String.valueOf(productCheckout.getQuantity()));
        productPrice.setText(String.valueOf(productCheckout.getPrice()) + " VND");

        return convertView;
    }
}

