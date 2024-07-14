package com.example.petshop.Products;

public class ProductDetail {
    private int productId;
    private String pname;
    private String description;
    private int price;

    private String imageUrl;

    public ProductDetail(int productId, String pname, String description, int price,  String imageUrl) {
        this.productId = productId;
        this.pname = pname;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
