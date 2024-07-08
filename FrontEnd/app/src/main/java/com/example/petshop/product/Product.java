package com.example.petshop.product;

public class Product {
    private int imageResId;
    private String description;
    private String price;

    public Product(int imageResId, String description, String price) {
        this.imageResId = imageResId;
        this.description = description;
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
