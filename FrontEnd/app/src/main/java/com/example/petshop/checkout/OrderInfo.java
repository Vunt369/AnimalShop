package com.example.petshop.checkout;

public class OrderInfo {
    private int userId;
    private int intoMoney;
    private int totalPrice;
    private int tranSportFee;


    // Constructor
    public OrderInfo(int userId, int intoMoney, int totalPrice, int tranSportFee ) {
        this.userId = userId;
        this.intoMoney = intoMoney;
        this.totalPrice = totalPrice;
        this.tranSportFee = tranSportFee;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIntoMoney() {
        return intoMoney;
    }

    public void setIntoMoney(int intoMoney) {
        this.intoMoney = intoMoney;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTranSportFee() {
        return tranSportFee;
    }

    public void setTranSportFee(int tranSportFee) {
        this.tranSportFee = tranSportFee;
    }


}
