package com.example.dell.yummy.model;

import com.google.gson.annotations.Expose;

public class User {
    @Expose
    private int user_id;
    @Expose
    private int login_id;
    @Expose
    private String login_username;
    @Expose
    private int login_pin;
    @Expose
    private String user_name;
    @Expose
    private String user_email;
    @Expose
    private String user_phone;
    @Expose
    private int user_wallet;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(int user_wallet) {
        this.user_wallet = user_wallet;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public String getLogin_username() {
        return login_username;
    }

    public void setLogin_username(String login_username) {
        this.login_username = login_username;
    }

    public int getLogin_pin() {
        return login_pin;
    }

    public void setLogin_pin(int login_pin) {
        this.login_pin = login_pin;
    }

}

