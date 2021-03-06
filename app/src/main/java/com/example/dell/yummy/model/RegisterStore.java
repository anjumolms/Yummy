package com.example.dell.yummy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterStore {
    @SerializedName("login_pin")
    @Expose
    private int loginPin;
    @SerializedName("login_username")
    @Expose
    private String loginUsername;
    @SerializedName("retail_account")
    @Expose
    private String retailAccount;
    @SerializedName("retail_name")
    @Expose
    private String retailName;
    @SerializedName("retail_number")
    @Expose
    private int retailNumber;
    @SerializedName("retail_wallet")
    @Expose
    private int retailWallet;
    @SerializedName("location_id")
    @Expose
    private int location_id;

    @SerializedName("retail_email")
    @Expose
    private String retailEmail;

    @SerializedName("retail_poc")
    @Expose
    private String retailPoc;
    public int getLoginPin() {
        return loginPin;
    }

    public void setLoginPin(int loginPin) {
        this.loginPin = loginPin;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getRetailAccount() {
        return retailAccount;
    }

    public void setRetailAccount(String retailAccount) {
        this.retailAccount = retailAccount;
    }

    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public int getRetailNumber() {
        return retailNumber;
    }

    public void setRetailNumber(int retailNumber) {
        this.retailNumber = retailNumber;
    }

    public int getRetailWallet() {
        return retailWallet;
    }

    public void setRetailWallet(int retailWallet) {
        this.retailWallet = retailWallet;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getRetailEmail() {
        return retailEmail;
    }

    public void setRetailEmail(String retailEmail) {
        this.retailEmail = retailEmail;
    }

    public String getRetailPoc() {
        return retailPoc;
    }

    public void setRetailPoc(String retailPoc) {
        this.retailPoc = retailPoc;
    }
}
