package com.example.petshop.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.petshop.Products.Product;
import com.example.petshop.R;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CartItem> cartItems;
    private Runnable updateTotalCostCallback;
    private boolean isEditing;

    public CartAdapter(Context context, ArrayList<CartItem> cartItems, Runnable updateTotalCostCallback) {
        this.context = context;
        this.cartItems = cartItems;
        this.updateTotalCostCallback = updateTotalCostCallback;
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
        EditText productQuantity = convertView.findViewById(R.id.productQuantity);
        Button increaseButton = convertView.findViewById(R.id.increaseButton);
        Button decreaseButton = convertView.findViewById(R.id.decreaseButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        CartItem item = cartItems.get(position);
        Product product = item.getProduct();
        Glide.with(context)
                .load(product.getImageUrl()) // Assuming product.getImage() returns a URL or file path as a string
                .into(productImage);
        productName.setText(product.getPname());
        productQuantity.setText(String.valueOf(item.getQuantity()));
        productPrice.setText("Price: $" + product.getPrice());

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setQuantity(item.getQuantity() + 1);
                updateTotalCostCallback.run();
                notifyDataSetChanged();
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    productQuantity.setText(String.valueOf(item.getQuantity()));
                    updateTotalCostCallback.run();
                    notifyDataSetChanged();
                } else {
                    new AlertDialog.Builder(context)
                            .setMessage("Do you want to remove this item?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                cartItems.remove(position);
                                updateTotalCostCallback.run();
                                notifyDataSetChanged();
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                item.setQuantity(1);
                                productQuantity.setText(String.valueOf(item.getQuantity()));
                                notifyDataSetChanged();
                            })
                            .show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you want to remove this item?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            cartItems.remove(position);
                            updateTotalCostCallback.run();
                            notifyDataSetChanged();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEditing) {
                    isEditing = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) {
                    isEditing = false;
                    try {
                        String inputText = s.toString();
                        if (inputText.isEmpty()) {
                            return;
                        }

                        int quantity = Integer.parseInt(inputText);
                        if (quantity <= 0) {
                            new AlertDialog.Builder(context)
                                    .setMessage("Do you want to remove this item?")
                                    .setPositiveButton("Yes", (dialog, which) -> {
                                        cartItems.remove(position);
                                        updateTotalCostCallback.run();
                                        notifyDataSetChanged();
                                    })
                                    .setNegativeButton("No", (dialog, which) -> {
                                        item.setQuantity(1);
                                        productQuantity.setText(String.valueOf(item.getQuantity()));
                                        notifyDataSetChanged();
                                    })
                                    .show();
                        } else {
                            item.setQuantity(quantity);
                            updateTotalCostCallback.run();
                        }
                    } catch (NumberFormatException e) {
                        item.setQuantity(1);
                        productQuantity.setText(String.valueOf(item.getQuantity()));
                    }
                }
            }
        });

        return convertView;
    }
}