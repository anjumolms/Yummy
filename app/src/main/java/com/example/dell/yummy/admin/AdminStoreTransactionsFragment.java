package com.example.dell.yummy.admin;


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

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.TransactionDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminStoreTransactionsFragment extends Fragment {

    private IAdminFragmentListener iAdminFragmentListener;
    private List<TransactionDetails> transactionDetailsList;
    private RecyclerView mRecyclerView;
    private AdminStoreTransactionAdapter mAdapter;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_TRANSACTION_DETAILS)) {
                updateData();
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
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_TRANSACTION_DETAILS);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        showStoreDetails();

    }

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();

        if (retrofitNetworksCalls != null) {
            List<TransactionDetails> details
                    = retrofitNetworksCalls.getTransactionList();

            mAdapter = new AdminStoreTransactionAdapter(getActivity(),
                    details, iAdminFragmentListener);
            //setting adapter to recyclerview
            mRecyclerView.setAdapter(mAdapter);

        }

    }
    private void updateData() {
        if (mAdapter != null) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                mAdapter.setData(retrofitNetworksCalls.getTransactionList());
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    public void addListener(IAdminFragmentListener iAdminFragmentListener) {

        this.iAdminFragmentListener = iAdminFragmentListener;

    }

}