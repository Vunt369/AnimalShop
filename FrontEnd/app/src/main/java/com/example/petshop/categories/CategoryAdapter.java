package com.example.petshop.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> categoriesList;

    public CategoryAdapter(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.row_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category category = categoriesList.get(position);

        holder.imgCatgory.setImageResource(category.getImage());

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCatgory;
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCatgory = itemView.findViewById(R.id.imgCategory);

        }
    }
}
