package com.example.dell.yummy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailerMenu {
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_price")
    @Expose
    private int itemPrice;
    @SerializedName("item_signature")
    @Expose
    private int itemSignature;
    @SerializedName("retail_id")
    @Expose
    private int retailId;

    @SerializedName("item_stock")
    @Expose
    private int stock;

    @SerializedName("item_promotion")
    @Expose
    private int promotion;

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

    public int getRetailId() {
        return retailId;
    }

    public void setRetailId(int retailId) {
        this.retailId = retailId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
}
