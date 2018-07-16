package com.example.dell.yummy.Retailer;

class TransactionDetails {

    private int userId;
    private int transactionId;
    private int totalAmount;

    public TransactionDetails(int userId, int transactionId, int totalAmount) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
