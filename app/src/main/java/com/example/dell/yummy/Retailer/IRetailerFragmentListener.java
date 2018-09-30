package com.example.dell.yummy.Retailer;

import com.example.dell.yummy.model.DishesDetails;

public interface IRetailerFragmentListener {
    void addFragment(int screenId);

    void loadEachTransactionFragment(int position);

    void showItemUpdatePopup(DishesDetails dishesDetails);
    void showSnackBar();
}
