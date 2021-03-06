package com.example.dell.yummy.admin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.user.store.UserStoresAdapter;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminStoresFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    IAdminFragmentListener mIAdminFragmentListener;
    List<StoreDetails> StoreList;
    AdminStoresAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_STORE_DETAILS)) {
                updateData();
            }
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_STORE_DETAILS_ERROR)) {
                stopRefereshing();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_stores, container,
                false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_admin_stores_fragment);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipe_admin_stores);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.tab_color,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_STORE_DETAILS);
        intentFilter.addAction(Constants.NOTIFY_STORE_DETAILS_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        showStoreDetails();

    }

    public void addListener(IAdminFragmentListener mIAdminFragmentListener) {
        this.mIAdminFragmentListener = mIAdminFragmentListener;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null) {
            List<StoreDetails> details = retrofitNetworksCalls.getStoreDetailsList();

            adapter = new AdminStoresAdapter(getActivity(),
                    details, mIAdminFragmentListener);
            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }

    }

    private void updateData() {
        if (adapter != null) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                adapter.setData(retrofitNetworksCalls.getStoreDetailsList());
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRefresh() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null && mIAdminFragmentListener != null) {
            int locationId = mIAdminFragmentListener.getLocationIdFromSharedPreferance();
            retrofitNetworksCalls.getStoreDetails(getActivity(), locationId);
        }


        swipeRefreshLayout.setRefreshing(true);
    }

    private void stopRefereshing() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

