package com.example.dell.yummy;

public class Constants {

    public static final int SCREEN_LOGIN =0;
    public static final int SCREEN_REGISTRATION =1;
    public static final int SCREEN_USER_HOME =2;
    public  static final int SCREEN_USER = 3;
    public static final int SCREEN_STORE_DETAILS = 4;
    public static final int SCREEN_PAYMENT_DETAILS = 5;
    public static final int SCREEN_USER_ADD_COINS = 6;
    public static final int SCREEN_USER_WALLET = 7;
    public static final int SCREEN_RETAILER_HOME = 8;
    public static final int SCREEN_RETAILER_TRANSACTION_DETAILS = 9;
    public static final int SCREEN_RETAILER_EACH_TRANSACTION_DETAILS =10;
    public static final int SCREEN_RETAILER_ADD_ITEMS =11;
    public static final int SCREEN_RETAILER_LIST_ITEMS =12;
    public static final int SCREEN_WALLET =13;
    public static final int SCREEN_CONFIRMATION = 14;
    public static final int SCREEN_ORDER_SUCCESSFULL = 15;
    public static final int SCREEN_ADMIN_HOME = 16;
    public static final int SCREEN_CONFIRM_ORDERS = 17;
    public static final int SCREEN_ADMIN_STORE_LIST = 18;
    public static final int SCREEN_ADMIN_TRANSACTION_DETAILS = 19;
    public static final int SCREEN_REGISTER_RETAILER = 20;

    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Simplified Coding Notification";
    public static final String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";

    //login fragment
    public static final String FIELD_EMPTY_WARNING = "This field cannot be empty";
    public static final String FIELD_PASSWORD_INCORRECT = "Password does not match";


    //SharedPreferance details
    public static final String SHARED_PREFERANCE_LOGIN_DETAILS = "LoginDetails";
    public static final String KEY_USER_NAME = "UserName";
    public static final String KEY_LOGIN_PIN = "UserPin";
    public static final String KEY_WALLET = "Wallet";
    public static final String KEY_ID = "Id";
    public static final String KEY_ROLE = "keyRole";

    //Database constants
    public static final String COLOUM_USER_NAME = "username";
    public static final String COLOUM_USER_TOKEN = "token";
    public static final String TABLE_NAME = "login_details";


    public static final String NOTIFY_STORE_DETAILS = "NOTIFY_STORE_DETAILS";
    public static final String NOTIFY_DISH_DETAILS = "NOTIFY_DISH_DETAILS";

    public static final String NOTIFY_REVIEW_DETAILS = "NOTIFY_REVIEW_DETAILS";
    public static final String NOTIFY_TRANSACTION_DETAILS = "NOTIFY_TRANSACTION_DETAILS";

    public static final String NOTIFY_ITEM_ADDED = "NOTIFY_ITEM_ADDED";
    public static final String NOTIFY_RETAILOR_DISH_DETAILS = "NOTIFY_RETAILOR_DISH_DETAILS";
    public static final String NOTIFY_LIST_ITEM_UPDATED = "NOTIFY_LIST_ITEM_UPDATED";
    public static final String KEY_LIST_UPDATE = "KEY_LIST_UPDATE";
    public static final String NOTIFY_LIST_ITEM_DELETED = "NOTIFY_LIST_ITEM_DELETED";
    public static final String NOTIFY_USER_CONFIRM_ORDER = "NOTIFY_USER_CONFIRM_ORDER";
    public static final String NOTIFY_WALLET_UPDATED = "NOTIFY_WALLET_UPDATED";
    public static final String NOTIFY_USER_DETAILS = "NOTIFY_USER_DETAILS";
    public static final String NOTIFY_RETAILER_DETAILS = "NOTIFY_RETAILER_DETAILS";
    public static final String NOTIFY_REVIEW_UPDATED = "NOTIFY_REVIEW_UPDATED";
    public static final String NOTIFY_STORE_ADDED = "NOTIFY_STORE_ADDED";
}
