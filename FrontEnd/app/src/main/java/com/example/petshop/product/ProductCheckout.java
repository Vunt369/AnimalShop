package com.example.petshop.product;

import java.io.Serializable;

public class ProductCheckout implements Serializable {
    private int productId;
    private int quantity;
    private int price;

    public ProductCheckout(int productId, int quantity, int price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int unitPrice) {
        this.price = price;
    }
}

