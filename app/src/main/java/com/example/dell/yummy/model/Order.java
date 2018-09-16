package com.example.dell.yummy.model;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    @Expose
    private int order_id;
    @Expose
    private int user_id	;
    @Expose
    private int retail_id;
    @Expose
    private int wallet_tran_id;
    @Expose
    private int order_item_count;
    @Expose
    private int order_value ;
    @Expose
    private Timestamp order_date;
    @Expose
    private String order_status;
    @Expose
    private String order_items_string;

    //private List<DishesDetails> order_items;

    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getRetail_id() {
        return retail_id;
    }
    public void setRetail_id(int retail_id) {
        this.retail_id = retail_id;
    }
    public int getWallet_tran_id() {
        return wallet_tran_id;
    }
    public void setWallet_tran_id(int wallet_tran_id) {
        this.wallet_tran_id = wallet_tran_id;
    }
    public int getOrder_item_count() {
        return order_item_count;
    }
    public void setOrder_item_count(int order_item_count) {
        this.order_item_count = order_item_count;
    }
    public int getOrder_value() {
        return order_value;
    }
    public void setOrder_value(int order_value) {
        this.order_value = order_value;
    }
    public Timestamp getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Timestamp order_date) {
        this.order_date = order_date;
    }
    public String getOrder_status() {
        return order_status;
    }
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
//    public List<Menu> getOrder_items() {
//        return order_items;
//    }
//    public void setOrder_items(List<Menu> order_items) {
//        this.order_items = order_items;
//    }
    public String getOrder_items_string() {
        return order_items_string;
    }
    //TODO
    public void setOrder_items_string(String order_items_string) {
        this.order_items_string = order_items_string;
    }
}
