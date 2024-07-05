package com.example.petshop.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.R;

import java.util.ArrayList;


public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CartItem> cartItems;

    public CartAdapter(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }
    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }
        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        Button increaseButton = convertView.findViewById(R.id.increaseButton);
        Button decreaseButton = convertView.findViewById(R.id.decreaseButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        CartItem item = cartItems.get(position);
        productImage.setImageResource(item.getImageResource());
        productName.setText(item.getProductName());
        productQuantity.setText(String.valueOf(item.getQuantity()));
        productPrice.setText("Price: $" + item.getProductPrice());

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setQuantity(item.getQuantity() + 1);
                notifyDataSetChanged();
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    notifyDataSetChanged();
                } else {
                    new AlertDialog.Builder(context)
                            .setMessage("Do you want to remove this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cartItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you want to remove this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cartItems.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }

}
