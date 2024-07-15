package com.example.petshop.Products;

import com.example.petshop.User.UserClient;
import com.example.petshop.User.UserService;

public class ProductRepository {
    public static ProductService getProductService(){
        return UserClient.getClient().create(ProductService.class);
    }
}
