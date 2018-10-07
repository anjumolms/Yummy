package com.example.dell.yummy.user.dishes;


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

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDishesFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private IUserFragmentListener miUserFragmentListener;
    private UserDishesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_DISH_DETAILS:
                        updateData();
                        break;
                    case Constants.NOTIFY_DISH_DETAILS_ERROR:
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
        View view = inflater.inflate(R.layout.fragment_user_dishes, container,
                false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipe_dishes_list);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.tab_color,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_DISH_DETAILS);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        intentFilter.addAction(Constants.NOTIFY_DISH_DETAILS_ERROR);
        showDishDetails();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void addListener(IUserFragmentListener miUserFragmentListener) {
        this.miUserFragmentListener = miUserFragmentListener;

    }

    private void stopProgress() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void showDishDetails() {
        RetrofitNetworksCalls networksCalls = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (networksCalls != null) {
            List<DishesDetails> dishesDetails = networksCalls.getDishDetailsList();

            adapter = new UserDishesAdapter(getActivity(),
                    dishesDetails, miUserFragmentListener);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }
    }

    private void updateData() {
        if (adapter != null) {
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                adapter.setData(calls.getDishDetailsList());
                adapter.notifyDataSetChanged();
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

        }
    }

    @Override
    public void onRefresh() {
        if (isNetworkAvailable()) {

            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null && miUserFragmentListener != null) {
                int locationId = miUserFragmentListener.getLocationIdFromSharedPreferance();
                retrofitNetworksCalls.getDishDetails(getActivity(), locationId);
            }
        } else {
            if (miUserFragmentListener != null) {
                miUserFragmentListener.showSnackBar();
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
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
}


