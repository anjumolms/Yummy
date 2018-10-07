package com.example.dell.yummy.admin;


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
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminStoreTransactionsFragment extends Fragment implements View.OnClickListener {

    private IAdminFragmentListener iAdminFragmentListener;
    private List<Order> transactionDetailsList;
    private RecyclerView mRecyclerView;
    private AdminStoreTransactionAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private StoreDetails storeDetails;
    private TextView mTextView;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_ALL_TRANSACTIONS)) {
                updateData();
            }
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_ALL_TRANSACTIONS_ERROR)) {
                dismissProgress();
            }
        }
    };


    public AdminStoreTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_store_transactions,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_admin_stores_transaction);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressDialog = new ProgressDialog(getActivity());
        mTextView = view.findViewById(R.id.tv_back_button_admin_store);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_ALL_TRANSACTIONS);
        intentFilter.addAction(Constants.NOTIFY_ALL_TRANSACTIONS_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null && storeDetails != null) {
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.show();
            calls.getAllTransactionDetails(getActivity(),
                    storeDetails.getRetailId());
        }
        mTextView.setOnClickListener(this);
        showStoreDetails();

    }

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();

        if (retrofitNetworksCalls != null) {
            List<Order> details
                    = retrofitNetworksCalls.getAllTransactionList();

            mAdapter = new AdminStoreTransactionAdapter(getActivity(),
                    details, iAdminFragmentListener);
            //setting adapter to recyclerview
            mRecyclerView.setAdapter(mAdapter);
             /*else {
                mProgressDialog.setMessage("Loading.....");
                mProgressDialog.show();

            }*/


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if(calls != null){
            calls.resetTransactionList();
        }
    }

    private void dismissProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void updateData() {
        dismissProgress();
        if (mAdapter != null) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                mAdapter.setData(retrofitNetworksCalls.getAllTransactionList());
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    public void addListener(IAdminFragmentListener iAdminFragmentListener) {

        this.iAdminFragmentListener = iAdminFragmentListener;

    }

    public void setStores(StoreDetails storeDetails) {
        this.storeDetails = storeDetails;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_button_admin_store:
                if(iAdminFragmentListener != null){
                    iAdminFragmentListener.onBackPress();
                }
                break;
        }
    }
}
