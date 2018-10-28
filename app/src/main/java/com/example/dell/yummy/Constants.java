package com.example.dell.yummy;

public class Constants {

    public static final int SCREEN_LOGIN = 0;
    public static final int SCREEN_REGISTRATION = 1;
    public static final int SCREEN_USER_HOME = 2;
    public static final int SCREEN_USER = 3;
    public static final int SCREEN_STORE_DETAILS = 4;
    public static final int SCREEN_PURCHASE_DETAILS = 5;
    public static final int SCREEN_USER_ADD_COINS = 6;
    public static final int SCREEN_USER_WALLET = 7;
    public static final int SCREEN_RETAILER_HOME = 8;
    public static final int SCREEN_RETAILER_TRANSACTION_DETAILS = 9;
    public static final int SCREEN_RETAILER_EACH_TRANSACTION_DETAILS = 10;
    public static final int SCREEN_RETAILER_ADD_ITEMS = 11;
    public static final int SCREEN_RETAILER_LIST_ITEMS = 12;
    public static final int SCREEN_WALLET = 13;
    public static final int SCREEN_CONFIRMATION = 14;
    public static final int SCREEN_ORDER_SUCCESSFULL = 15;
    public static final int SCREEN_ADMIN_HOME = 16;
    public static final int SCREEN_CONFIRM_ORDERS = 17;
    public static final int SCREEN_ADMIN_STORE_LIST = 18;
    public static final int SCREEN_ADMIN_TRANSACTION_DETAILS = 19;
    public static final int SCREEN_REGISTER_RETAILER = 20;
    public static final int SCREEN_PURCHASE_HISTORY_ITEM = 21;
    public static final int SCREEN_REFUND_FRAGMENT = 22;
    public static final int SCREEN_ADMIN_VIEWPAGER = 23;
    public static final int SCREEN_ALL_STORES_LIST = 24;
    public static final int SCREEN_ADD_ADMIN = 25;
    public static final int SCREEN_PROFILE_EDIT = 26;

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
    public static final String KEY_LOCATION = "location";

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
    public static final String NOTIFY_TRANSACTION_ORDER = "NOTIFY_TRANSACTION_ORDER";
    public static final String NOTIFY_GET_LOCATION = "NOTIFY_GET_LOCATION";
    public static final String NOTIFY_DISH_DETAILS_ERROR = "NOTIFY_DISH_DETAILS_ERROR";
    public static final String NOTIFY_STORE_DETAILS_ERROR = "NOTIFY_STORE_DETAILS_ERROR";
    public static final String NOTIFY_REVIEW_DETAILS_ERROR = "NOTIFY_REVIEW_DETAILS_ERROR";
    public static final String NOTIFY_USER_CONFIRM_ORDER_ERROR = "NOTIFY_USER_CONFIRM_ORDER_ERROR";
    public static final String NOTIFY_ITEM_ADDED_ERROR = "NOTIFY_ITEM_ADDED_ERROR";
    public static final String NOTIFY_UPDATE_ITEM_ERROR = "NOTIFY_UPDATE_ITEM_ERROR";
    public static final String NOTIFY_WALLET_UPDATED_ERROR = "NOTIFY_WALLET_UPDATED_ERROR";
    public static final String NOTIFY_CONFIRMED_TRANSACTIONS = "NOTIFY_CONFIRMED_TRANSACTIONS";
    public static final String NOTIFY_CONFIRMED_TRANSACTIONS_ERROR = "NOTIFY_CONFIRMED_TRANSACTIONS_ERROR";
    public static final String NOTIFY_ALL_TRANSACTIONS = "NOTIFY_ALL_TRANSACTIONS";
    public static final String NOTIFY_ALL_TRANSACTIONS_ERROR = "NOTIFY_ALL_TRANSACTIONS_ERROR";
    public static final String NOTIFY_GET_ALL_LOCATIONS = "NOTIFY_GET_ALL_LOCATIONS";
    public static final String NOTIFY_GET_ALL_LOCATIONS_LIST = "NOTIFY_GET_ALL_LOCATIONS";
    public static final String NOTIFY_GET_ALL_LOCATIONS_ERROR = "NOTIFY_GET_ALL_LOCATIONS_ERROR";
    public static final String NOTIFY_GET_PURCHASE_HISTORY = "NOTIFY_GET_PURCHASE_HISTORY";
    public static final String NOTIFY_GET_PURCHASE_HISTORY_ERROR = "NOTIFY_GET_PURCHASE_HISTORY_ERROR";
    public static final String NOTIFY_TRANSACTION_ORDER_ERROR = "NOTIFY_TRANSACTION_ORDER_ERROR";
    public static final String NOTIFY_REFUND_ERROR = "NOTIFY_REFUND_ERROR";
    public static final String NOTIFY_REFUND = "NOTIFY_REFUND";
    public static final String NOTIFY_ALL_STORE_DETAILS = "NOTIFY_ALL_STORE_DETAILS";
    public static final String NOTIFY_ALL_STORE_DETAILS_ERROR = "NOTIFY_ALL_STORE_DETAILS_ERROR";
    public static final String NOTIFY_ADD_ADMIN = "NOTIFY_ADD_ADMIN";
    public static final String NOTIFY_ADD_ADMIN_ERROR = "NOTIFY_ADD_ADMIN_ERROR";
    public static final String NOTIFY_GET_USER_BY_NUMBER = "NOTIFY_GET_USER_BY_NUMBER";
    public static final String NOTIFY_GET_USER_BY_NUMBER_ERROR = "NOTIFY_GET_USER_BY_NUMBER_ERROR";
    public static final String INVALID_EMAIL_WARNING = "Enter Valid Email";
    public static final String MOBILE_NUMBER_WARNING = "Enter 10 digit Mobile Number";
    public static final String PASSWORD_MINIMUM_WARNING = "Minimum 4 Digits Required";
    public static final String KEY_RETAIL_NAME = "KEY_RETAIL_NAME";
    public static final String SELECT_LOCATION_WARNING = "Select Store Location";
    public static final String INVALID_UST_EMAIL_WARNING = "Enter Valid UST Email Address";
    public static final String NOTIFY_STORE_ADDED_ERROR = "NOTIFY_STORE_ADDED_ERROR";
    public static final String NOTIFY_PROFILE_DETAILS_ERROR = "NOTIFY_PROFILE_DETAILS_ERROR";
    public static final String NOTIFY_PROFILE_DETAILS_UPDATE = "NOTIFY_PROFILE_DETAILS_UPDATE";
    public static final String NOTIFY_UPDATE_DELIVERY = "NOTIFY_UPDATE_DELIVERY";
    public static final String NOTIFY_UPDATE_DELIVERY_ERROR = "NOTIFY_UPDATE_DELIVERY_ERROR";
    public static final String NOTIFY_UPDATE_USER_WALLET = "NOTIFY_UPDATE_USER_WALLET";
    public static final String NOTIFY_UPDATE_USER_WALLET_ERROR = "NOTIFY_UPDATE_USER_WALLET_ERROR";
    public static final String NOTIFY_REVIEW_UPDATED_ERROR = "NOTIFY_REVIEW_UPDATED_ERROR";

    public static final String AUTHUSERNAME = "ismpost";
    public static final String AUTHPASS = "pass1";
}
