package com.example.petshop.product;

import java.io.Serializable;

public class CheckoutReponse implements Serializable {

        private boolean isSuccess;
        private String message;
        private String data;

        public CheckoutReponse(boolean isSuccess, String message, String data) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

}
