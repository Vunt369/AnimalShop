package com.example.petshop.User;

public class UserRepository {
    public static UserService getUserService(){
        return UserClient.getClient().create(UserService.class);
    }
}
