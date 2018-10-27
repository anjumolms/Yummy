package com.example.dell.yummy.Retailer;

import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;

public interface IRetailerFragmentListener {
    void addFragment(int screenId);

    void loadEachTransactionFragment(Order details);

    void showItemUpdatePopup(DishesDetails dishesDetails);
    void showSnackBar();

    void showNavigationDrawer();
    void onBackPress();
}
