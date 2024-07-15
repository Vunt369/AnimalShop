package com.example.petshop.product;


import com.example.petshop.Products.Product;

import java.io.Serializable;
import java.util.List;

public class CheckoutRequest implements Serializable {
    private int userId;
    private int intoMoney;
    private int totalPrice;
    private int tranSportFee;
    private ShipmentDetailDTO shipmentDetailDTO;
    private List<ProductCheckout> orderDetailList;

    public CheckoutRequest(int userId, int intoMoney, int totalPrice, int tranSportFee, ShipmentDetailDTO shipmentDetailDTO, List<ProductCheckout> orderDetailList) {
        this.userId = userId;
        this.intoMoney = intoMoney;
        this.totalPrice = totalPrice;
        this.tranSportFee = tranSportFee;
        this.shipmentDetailDTO = shipmentDetailDTO;
        this.orderDetailList = orderDetailList;
    }

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

    public ShipmentDetailDTO getShipmentDetailDTO() {
        return shipmentDetailDTO;
    }

    public void setShipmentDetailDTO(ShipmentDetailDTO shipmentDetailDTO) {
        this.shipmentDetailDTO = shipmentDetailDTO;
    }

    public List<ProductCheckout> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<ProductCheckout> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public static class ShipmentDetailDTO implements Serializable {
        private String fullName;
        private String address;
        private String phoneNumber;

        public ShipmentDetailDTO(String fullName, String address, String phoneNumber) {
            this.fullName = fullName;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static class OrderDetail implements Serializable {
        private int productId;
        private int quantity;
        private int unitPrice;

        public OrderDetail(int productId, int quantity, int unitPrice) {
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
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

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }
    }


}

