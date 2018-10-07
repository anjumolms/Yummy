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
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminListStoreFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    IAdminFragmentListener mIAdminFragmentListener;
    List<StoreDetails> StoreList;
    AdminListStoreAdapter adapter;
    ProgressDialog progressDialog;
    TextView back;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_ALL_STORE_DETAILS)) {
                showStoreDetails();
            }

            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_ALL_STORE_DETAILS_ERROR)) {
                dismissProgress();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_list_store, container,
                false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_admin_stores_list_fragment);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.id_back_admin_list_store);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_ALL_STORE_DETAILS);
        intentFilter.addAction(Constants.NOTIFY_ALL_STORE_DETAILS_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            progressDialog.setMessage("Loading........");
            progressDialog.show();
            calls.getAllStoreDetails(getActivity());
        }
        back.setOnClickListener(this);
        //showStoreDetails();

    }

    private void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void addListener(IAdminFragmentListener mIAdminFragmentListener) {
        this.mIAdminFragmentListener = mIAdminFragmentListener;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showStoreDetails() {
        dismissProgress();
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null) {

            List<RetailerDetails> details = retrofitNetworksCalls.getAllStoreListCollection();

            adapter = new AdminListStoreAdapter(getActivity(),
                    details, mIAdminFragmentListener);
            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }

    }

//    private void updateData() {
//        if (adapter != null) {
//            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
//                    .getRetrofitNetworksCallsObject();
//            if (retrofitNetworksCalls != null) {
//                adapter.setData(retrofitNetworksCalls);
//                adapter.notifyDataSetChanged();
//            }
//
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_back_admin_list_store:
                if (mIAdminFragmentListener != null) {
                    mIAdminFragmentListener.onBackPress();
                }
                break;
        }
    }
}
