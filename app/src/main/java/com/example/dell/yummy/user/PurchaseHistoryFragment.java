package com.example.dell.yummy.user;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseHistoryFragment extends Fragment implements View.OnClickListener {
    private IUserFragmentListener mIUserFragmentListener;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private PurchaseHistoryAdapter purchaseHistoryAdapter;
    private TextView backBt;

    public PurchaseHistoryFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_GET_PURCHASE_HISTORY:
                        updateData();
                        break;
                    case Constants.NOTIFY_GET_PURCHASE_HISTORY_ERROR:
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
        View view = inflater.inflate(R.layout.fragment_payment_details,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_purchase_history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        backBt = view.findViewById(R.id.back_purchase_history);
        mProgressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_GET_PURCHASE_HISTORY);
        intentFilter.addAction(Constants.NOTIFY_GET_PURCHASE_HISTORY_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        RetrofitNetworksCalls calls
                = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.show();
            calls.getPurchaseHistory(getContext());
        }
        backBt.setOnClickListener(this);
        setData();

    }

    private void setData() {
        RetrofitNetworksCalls calls
                = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            List<Order> purchaseHistoryList = calls.getPurchaseHistoryList();

            purchaseHistoryAdapter = new PurchaseHistoryAdapter(getActivity(), purchaseHistoryList,
                    mIUserFragmentListener);
            mRecyclerView.setAdapter(purchaseHistoryAdapter);

        }
    }

    public void addListener(IUserFragmentListener mIUserFragmentListener) {
        this.mIUserFragmentListener = mIUserFragmentListener;
    }

    private void stopProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void updateData() {
        stopProgress();
        if (purchaseHistoryAdapter != null) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                purchaseHistoryAdapter.setData(retrofitNetworksCalls.getPurchaseHistoryList());
                purchaseHistoryAdapter.notifyDataSetChanged();

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_purchase_history:
                if (mIUserFragmentListener != null) {
                    mIUserFragmentListener.onBackPress();
                }
                break;
        }
    }
}
