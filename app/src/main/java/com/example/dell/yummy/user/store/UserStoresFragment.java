package com.example.dell.yummy.user.store;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.RelativeLayout;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserStoresFragment extends
        Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    IUserFragmentListener miUserFragmentListener;
    List<StoreDetails> StoreList;
    UserStoresAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout userStoresFragment;


    public UserStoresFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_STORE_DETAILS:
                        updateData();
                        break;
                    case Constants.NOTIFY_STORE_DETAILS_ERROR:
                        stopProgress();
                        break;
                }
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
        View view = inflater.inflate(R.layout.fragment_user_stores, container,
                false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_stores);
        recyclerView.setHasFixedSize(true);
        userStoresFragment = view.findViewById(R.id.rl_user_stores_fragment);
        swipeRefreshLayout = view.findViewById(R.id.swipe_user_stores);
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

    public void addListener(IUserFragmentListener miUserFragmentListener) {
        this.miUserFragmentListener = miUserFragmentListener;
    }

    private void stopProgress() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

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

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null) {
            List<StoreDetails> details = retrofitNetworksCalls.getStoreDetailsList();

            adapter = new UserStoresAdapter(getActivity(),
                    details, miUserFragmentListener);
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
        if (isNetworkAvailable()) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null && miUserFragmentListener != null) {
                int locationId = miUserFragmentListener.getLocationIdFromSharedPreferance();
                retrofitNetworksCalls.getStoreDetails(getActivity(), locationId);
            }
        } else {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (miUserFragmentListener != null) {
                miUserFragmentListener.showSnackBar();
            }
        }

    }
}
