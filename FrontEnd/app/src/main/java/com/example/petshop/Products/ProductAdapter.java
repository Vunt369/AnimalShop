package com.example.petshop.Products;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petshop.R;
import com.example.petshop.categories.Category;
import com.example.petshop.categories.CategoryAdapter;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> productList;
    private ArrayList<String> cartItems = new ArrayList<>();
    private Context context;
    public ProductAdapter(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.cartItems = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.row_product_homepage, parent, false);
        ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtName.setText(product.getPname());
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
        holder.imgProduct.setOnLongClickListener(v -> {
            if (cartItems.contains(product.getPname())){
                // Product already exists in the cart, update quantity
                int index = cartItems.indexOf(product.getPname());
                updateCartItemQuantity(index, product.getPname()); // Increment quantity by product name
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add to Cart")
                        .setMessage("Do you want to add " + product.getPname() + " to the cart?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            float price = product.getPrice();
                            cartItems.add(product.getPname());
                            Toast.makeText(context, product.getPname() + " was added to the cart", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            return true;
        });
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        TextView txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);

        }
    }
    public ArrayList<String> getCartItems() {
        return cartItems;
    }
    private void updateCartItemQuantity(int index, String productName) {
        String existingProduct = cartItems.get(index);
        notifyDataSetChanged();
    }
}
