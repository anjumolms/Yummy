package com.example.dell.yummy.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAddCoinsFragment extends Fragment {

    IUserFragmentListener iUserFragmentListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_add_coins, container,
                false);


        RetrofitNetworksCalls calls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (calls != null) {
            List<DishesDetails> details = calls.getDishDetailsList();
            List<StoreDetails> storeDetails = calls.getStoreDetailsList();
            if(details != null && !details.isEmpty()){
                int size = details.size();
            }
            if(storeDetails != null && !storeDetails.isEmpty()){
                int size = storeDetails.size();
            }

        }
        return view;
    }

    public void addListener(IUserFragmentListener iUserFragmentListener) {
        this.iUserFragmentListener = iUserFragmentListener;
    }


}
