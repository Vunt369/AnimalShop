package com.example.petshop.User;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String usename;
    private String password;

    public LoginRequest(String usename, String password) {
        this.usename = usename;
        this.password = password;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
