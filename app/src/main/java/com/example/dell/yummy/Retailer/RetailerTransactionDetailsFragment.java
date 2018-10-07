package com.example.dell.yummy.Retailer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class RetailerTransactionDetailsFragment
        extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private IRetailerFragmentListener retailerFragmentListener;
    private List<Order> transactionDetailsList;
    private RecyclerView mRecyclerView;
    private TransactionDetailsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mTextView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_TRANSACTION_DETAILS)) {
                updateData();
            }
        }
    };


    public RetailerTransactionDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_transaction_details,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_transaction_details);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextView  = view.findViewById(R.id.tool_retailer);

        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.tab_color,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_TRANSACTION_DETAILS);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        mTextView.setOnClickListener(this);
        showStoreDetails();

    }

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();

        if (retrofitNetworksCalls != null) {
            List<Order> details
                    = retrofitNetworksCalls.getTransactionList();

            mAdapter = new TransactionDetailsAdapter(getActivity(),
                    details, retailerFragmentListener);
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

                swipeRefreshLayout.setRefreshing(false);

            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if(calls != null){
            calls.resetRetailerTransactionList();
        }
    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {

        this.retailerFragmentListener = retailerFragmentListener;

    }

    @Override
    public void onRefresh() {
        if (isNetworkAvailable()) {

            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                                Context.MODE_PRIVATE);
                int id = 0;
                if (sharedPreferences != null) {
                    id = sharedPreferences.getInt(Constants.KEY_ID, 0);
                    calls.getTransactionPendingOrders(getActivity(), id);
                }
            }
        } else {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (retailerFragmentListener != null) {
                retailerFragmentListener.showSnackBar();
            }

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager ConnectionManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tool_retailer:
                if(retailerFragmentListener != null){
                    retailerFragmentListener.showNavigationDrawer();
                }
                break;
        }
    }
}
