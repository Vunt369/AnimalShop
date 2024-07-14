package com.example.petshop.product;

public class Product {
    private int imageResId;
    private String description;
    private String quantity;
    private String price;

    public Product(int imageResId, String description, String quantity, String price) {
        this.imageResId = imageResId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
