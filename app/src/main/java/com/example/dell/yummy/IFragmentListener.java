package com.example.dell.yummy;

import com.example.dell.yummy.webservice.DishesDetails;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.List;

public interface IFragmentListener {
    void addFragment(int screenId);
    void passStoreDetails(int screenId, StoreDetails storeDetails);
    void addPopup(DishesDetails dishesDetails);
    void loadConformationFragment(List<DishesDetails> dishesDetails);
    void showDialog();
    int getUserId();
    String getUserName();

}