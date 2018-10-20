package com.example.dell.yummy.model;

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
    @SerializedName("avg_rating")
    @Expose
    private int review;
    @SerializedName("count")
    @Expose
    private int counter = 0;

    @SerializedName("retail_name")
    @Expose
    private String retail_name;

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @SerializedName("item_promotion")
    @Expose
    private int promotion;
    @SerializedName("menu_type")
    @Expose
    private int menuType;

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

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

    public String getRetail_name() {
        return retail_name;
    }

    public void setRetail_name(String retail_name) {
        this.retail_name = retail_name;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }
}
