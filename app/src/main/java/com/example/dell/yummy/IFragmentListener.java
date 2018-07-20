package com.example.dell.yummy;

import com.example.dell.yummy.webservice.StoreDetails;

public interface IFragmentListener {
    void addFragment(int screenId);
    void passStoreDetails(int screenId, StoreDetails storeDetails);
}
