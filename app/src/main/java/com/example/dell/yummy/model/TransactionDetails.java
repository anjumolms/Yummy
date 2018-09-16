package com.example.dell.yummy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails{
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("order_id")
        @Expose
        private int orderId;
        @SerializedName("order_item_count")
        @Expose
        private int orderItemCount;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("order_value")
        @Expose
        private int orderValue;
        @SerializedName("retail_id")
        @Expose
        private int retailId;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("wallet_tran_id")
        @Expose
        private int walletTranId;
        @SerializedName("order_items_string")
        @Expose
        private String orderItemsString;

        public String getOrderItemsString() { return orderItemsString; }

        public void setOrderItemsString(String orderItemsString) { this.orderItemsString = orderItemsString; }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getOrderItemCount() {
            return orderItemCount;
        }

        public void setOrderItemCount(int orderItemCount) {
            this.orderItemCount = orderItemCount;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOrderValue() {
            return orderValue;
        }

        public void setOrderValue(int orderValue) {
            this.orderValue = orderValue;
        }

        public int getRetailId() {
            return retailId;
        }

        public void setRetailId(int retailId) {
            this.retailId = retailId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWalletTranId() {
            return walletTranId;
        }

        public void setWalletTranId(int walletTranId) {
            this.walletTranId = walletTranId;
        }

    }

