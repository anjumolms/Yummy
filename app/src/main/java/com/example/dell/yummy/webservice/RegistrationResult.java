package com.example.dell.yummy.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResult {

    @SerializedName("login_id")
        @Expose
        private int loginId;
        @SerializedName("login_pin")
        @Expose
        private int loginPin;
        @SerializedName("login_role")
        @Expose
        private int loginRole;
        @SerializedName("login_username")
        @Expose
        private String loginUsername;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("user_wallet")
        @Expose
        private int userWallet;

        public int getLoginId() {
            return loginId;
        }

        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }

        public int getLoginPin() {
            return loginPin;
        }

        public void setLoginPin(int loginPin) {
            this.loginPin = loginPin;
        }

        public int getLoginRole() {
            return loginRole;
        }

        public void setLoginRole(int loginRole) {
            this.loginRole = loginRole;
        }

        public String getLoginUsername() {
            return loginUsername;
        }

        public void setLoginUsername(String loginUsername) {
            this.loginUsername = loginUsername;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public int getUserWallet() {
            return userWallet;
        }

        public void setUserWallet(int userWallet) {
            this.userWallet = userWallet;
        }

    }

