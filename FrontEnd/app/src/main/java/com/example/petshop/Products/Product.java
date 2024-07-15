package com.example.petshop.Products;

import java.io.Serializable;

public class Product implements Serializable {


    private int productId; 
    private String pname;
    private int price;
    private String imageUrl;


    public Product(int productId, String pname, int price, String imageUrl) {
        this.productId = productId;
        this.pname = pname;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
