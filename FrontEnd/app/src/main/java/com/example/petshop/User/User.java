package com.example.petshop.User;

import java.io.Serializable;
import java.time.DateTimeException;
import java.util.Date;

public class User implements Serializable {

    private String usename;
    private String password;
    private String fullName;

    private String email;
    private String phone;
    private String gender;
  //  private Date createAt;




    public User(String usename, String password, String fullName, String email, String phone, String gender) {
        this.usename = usename;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }



    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
