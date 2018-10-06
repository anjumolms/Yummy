package com.example.dell.yummy.user;

import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.model.UserReview;

import java.util.List;

public interface IUserFragmentListener {
    void addFragment(int screenId);
    void passStoreDetails(int screenId, StoreDetails storeDetails);
    void addPopup(DishesDetails dishesDetails);
    void loadConformationFragment(List<DishesDetails> dishesDetails);
    void showDialog(UserReview userReview);
    void showSnackBar();
    void showNavigationDrawer();
    int getLocationIdFromSharedPreferance();

    void showPurchaseHistory(Order order);

    void onBackPress();
}