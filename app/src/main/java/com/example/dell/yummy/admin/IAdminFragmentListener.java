package com.example.dell.yummy.admin;

import com.example.dell.yummy.model.StoreDetails;

public interface IAdminFragmentListener {
    void addFragment(int screenId);

    void passStoreDetails(int screenRetailerTransactionDetails,
                          StoreDetails storeDetails);

    void showNavigationDrawer();

    void onBackPress();
}
