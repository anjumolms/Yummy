package com.example.dell.yummy.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishesDetails {

        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("item_price")
        @Expose
        private int itemPrice;
        @SerializedName("item_signature")
        @Expose
        private int itemSignature;
        @SerializedName("item_sold_count")
        @Expose
        private int itemSoldCount;
        @SerializedName("item_stock")
        @Expose
        private int itemStock;
        @SerializedName("menu_id")
        @Expose
        private int menuId;
        @SerializedName("retail_id")
        @Expose
        private int retailId;
        @SerializedName("item_review")
        @Expose
        private int review;

        private int counter = 0;

    public int getReview() { return review; }

    public void setReview(int review) { this.review = review; }

    public int getCounter() { return counter; }

    public void setCounter(int counter) { this.counter = counter; }

    public String getItemName() {
            return itemName;
        }


        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
        }

        public int getItemSignature() {
            return itemSignature;
        }

        public void setItemSignature(int itemSignature) {
            this.itemSignature = itemSignature;
        }

        public int getItemSoldCount() {
            return itemSoldCount;
        }

        public void setItemSoldCount(int itemSoldCount) {
            this.itemSoldCount = itemSoldCount;
        }

        public int getItemStock() {
            return itemStock;
        }

        public void setItemStock(int itemStock) {
            this.itemStock = itemStock;
        }

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public int getRetailId() {
            return retailId;
        }

        public void setRetailId(int retailId) {
            this.retailId = retailId;
        }

    }
