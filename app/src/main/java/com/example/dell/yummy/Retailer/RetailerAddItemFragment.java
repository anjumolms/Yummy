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
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
    private SwitchCompat mSignatureDish;
    private SwitchCompat mCombo;
    private Button mAddItem;
    private EditText stock;
    private ProgressDialog progressDialog;
    private TextView back;

    public RetailerAddItemFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_ITEM_ADDED:
                        dismissProgress();
                        break;
                    case Constants.NOTIFY_ITEM_ADDED_ERROR:
                        stopProgress();
                        break;
                }
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
        mCombo = view.findViewById(R.id.et_combo_dish);
        stock = view.findViewById(R.id.stock);
        back = view.findViewById(R.id.add_item_back);
        progressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_ITEM_ADDED);
        intentFilter.addAction(Constants.NOTIFY_ITEM_ADDED_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        mAddItem.setOnClickListener(this);
        back.setOnClickListener(this);
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
            case R.id.add_item_back:
                if (retailerFragmentListener != null) {
                    retailerFragmentListener.onBackPress();
                }
                break;
            default:
                break;
        }
    }

    private void addItemToList() {
        RetailerMenu retailerMenu = new RetailerMenu();
        String itemName = mItimeName.getText().toString().trim();
        String itemCost = mItemPrice.getText().toString().trim();
        String stockNumber = stock.getText().toString().trim();

        if (TextUtils.isEmpty(itemName)) {
            mItimeName.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (TextUtils.isEmpty(itemCost)) {
            mItemPrice.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (TextUtils.isEmpty(stockNumber)) {
            stock.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        int signature = 0;
        if (mSignatureDish.isChecked()) {
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
        retailerMenu.setStock(Integer.parseInt(stockNumber));
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
        mSignatureDish.setChecked(false);
        mItimeName.setText("");
        mItemPrice.setText("");
        stock.setText("");
        mItimeName.requestFocus();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getRetailerMenuList(getActivity(), 0);
        }
    }

    private void stopProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
