package com.example.petshop.cart;

public class CartItem {
    private String productName;
    private int quantity;
    private int imageResource;
    private int productPrice;

    public CartItem(String productName, int quantity, int imageResource, int productPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.imageResource = imageResource;
        this.productPrice = productPrice;
    }
    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
