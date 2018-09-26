package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.model.RetailerMenu;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerAddItemFragment extends Fragment implements View.OnClickListener {

    IRetailerFragmentListener retailerFragmentListener;
    private EditText mItimeName;
    private EditText mItemPrice;
    private EditText mSignatureDish;
    private Button mAddItem;
    private ProgressDialog progressDialog;

    public RetailerAddItemFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_ITEM_ADDED)) {
                dismissProgress();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_add_item, container,
                false);
        initViews(view);
        return view;

    }

    private void initViews(View view) {
        mItimeName = view.findViewById(R.id.et_retailer_item);
        mItemPrice = view.findViewById(R.id.et_retailer_cost);
        mSignatureDish = view.findViewById(R.id.et_signature_dish);
        mAddItem = view.findViewById(R.id.bt_add_item_retailer);
        progressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_ITEM_ADDED);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        mAddItem.setOnClickListener(this);
    }


    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.retailerFragmentListener = retailerFragmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_item_retailer:
                addItemToList();
                break;
            default:
                break;
        }
    }

    private void addItemToList() {
        RetailerMenu retailerMenu = new RetailerMenu();
        String itemName = mItimeName.getText().toString().trim();
        String itemCost = mItemPrice.getText().toString().trim();
        String signatureDish = mSignatureDish.getText().toString().trim();
        int signature = 0;
        if (signatureDish.equalsIgnoreCase("yes")) {
            signature = 1;
        }
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                        Context.MODE_PRIVATE);
        int id = 0;
        if (sharedPreferences != null) {
            id = sharedPreferences.getInt(Constants.KEY_ID, 0);
        }
        retailerMenu.setItemName(itemName);
        retailerMenu.setItemPrice(Integer.parseInt(itemCost));
        retailerMenu.setItemSignature(signature);
        retailerMenu.setRetailId(id);
        RetrofitNetworksCalls calls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.addItemsToList(retailerMenu, getActivity());
            progressDialog.setMessage("Please wait..");
            progressDialog.show();

        }

    }

    private void dismissProgress() {
        progressDialog.dismiss();
        mSignatureDish.setText("");
        mItimeName.setText("");
        mItemPrice.setText("");
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if(calls != null){
            calls.getRetailerMenuList(getActivity(), 0);
        }
    }

}
