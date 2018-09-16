package com.example.dell.yummy.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreDetails implements Serializable {
    @SerializedName("login_id")
        @Expose
        private int loginId;
        @SerializedName("retail_id")
        @Expose
        private int retailId;
        @SerializedName("retail_name")
        @Expose
        private String retailName;
        @SerializedName("retail_number")
        @Expose
        private int retailNumber;
        @SerializedName("retail_wallet")
        @Expose
        private int retailWallet;

        public int getLoginId() {
            return loginId;
        }

        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }

        public int getRetailId() {
            return retailId;
        }

        public void setRetailId(int retailId) {
            this.retailId = retailId;
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

    }
