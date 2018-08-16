package com.example.dell.yummy.user.reviews;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserReview {
    @SerializedName("menu_id")
        @Expose
        private int menuId;
        @SerializedName("order_id")
        @Expose
        private int orderId;
        @SerializedName("order_item")
        @Expose
        private String orderItem;
        @SerializedName("retail_id")
        @Expose
        private int retailId;
        @SerializedName("retail_numer")
        @Expose
        private int retailNumer;
        @SerializedName("review_id")
        @Expose
        private int reviewId;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("user_rating")
        @Expose
        private int userRating;

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(String orderItem) {
            this.orderItem = orderItem;
        }

        public int getRetailId() {
            return retailId;
        }

        public void setRetailId(int retailId) {
            this.retailId = retailId;
        }

        public int getRetailNumer() {
            return retailNumer;
        }

        public void setRetailNumer(int retailNumer) {
            this.retailNumer = retailNumer;
        }

        public int getReviewId() {
            return reviewId;
        }

        public void setReviewId(int reviewId) {
            this.reviewId = reviewId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserRating() {
            return userRating;
        }

        public void setUserRating(int userRating) {
            this.userRating = userRating;
        }

    }

