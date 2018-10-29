package com.example.dell.yummy.user;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.user.store.ConfirmationAdapter;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseHistoryItemFragment extends Fragment implements View.OnClickListener {
    private TextView mStoreName;
    private RecyclerView mRecyclerView;
    private TextView mTotal;
    private Order orders;
    private IUserFragmentListener mIUserFragmentListener;
    private ProgressDialog mProgressDialog;
    private TextView back;


    public PurchaseHistoryItemFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_TRANSACTION_ORDER:
                        updateData();
                        break;
                    case Constants.NOTIFY_TRANSACTION_ORDER_ERROR:
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
        View view = inflater.inflate(R.layout.fragment_purchase_history_item,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mStoreName = view.findViewById(R.id.purchase_items_store_name);
        mRecyclerView = view.findViewById(R.id.rv_purchase_items_list);
        mTotal = view.findViewById(R.id.tv_purchase_item_total_price);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.back_purchase_item);

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_TRANSACTION_ORDER);
        intentFilter.addAction(Constants.NOTIFY_TRANSACTION_ORDER_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        back.setOnClickListener(this);
        showDishDetails();


    }

    private void showDishDetails() {
        if (orders != null) {

            mTotal.setText("₹ " + orders.getOrder_value());
            mStoreName.setText("" + orders.getRetail_name());
            RetrofitNetworksCalls calls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (calls != null) {
                calls.getTransactionOrders(getActivity(), orders.getOrder_id());
                mProgressDialog.setMessage("Loading..........");
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);
            }
        }
    }

    private void stopProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void updateData() {
        stopProgress();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            Order order = calls.getOrderInfo();
            if (order != null) {
                List<DishesDetails> dishesDetails = order.getMenus();
                if (dishesDetails != null) {
                    ConfirmationAdapter mConfirmationAdapter = new ConfirmationAdapter(getActivity(), dishesDetails);
                    //setting adapter to recyclerview
                    mRecyclerView.setAdapter(mConfirmationAdapter);
                }
            }
        }
    }

    public void setOrderDetails(Order orderDetails) {
        this.orders = orderDetails;
    }

    public void addListener(IUserFragmentListener iUserFragmentListener) {
        mIUserFragmentListener = iUserFragmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_purchase_item:
                if(mIUserFragmentListener != null){
                    mIUserFragmentListener.onBackPress();
                }
                break;
        }
    }
}
