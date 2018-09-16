package com.example.dell.yummy;

import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.StoreDetails;

import java.util.List;

public interface IFragmentListener {
    void addFragment(int screenId);
    void passStoreDetails(int screenId, StoreDetails storeDetails);
    void addPopup(DishesDetails dishesDetails);
    void loadConformationFragment(List<DishesDetails> dishesDetails);
    void showDialog();
}