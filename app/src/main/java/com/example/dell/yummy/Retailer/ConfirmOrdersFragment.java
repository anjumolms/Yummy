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
public class ConfirmOrdersFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private TransactionDetailsAdapter mTransactionDetailsAdapter;
    private IRetailerFragmentListener mFragmentListener;
    private ProgressDialog mProgressDialog;
    private TextView back;


    public ConfirmOrdersFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_CONFIRMED_TRANSACTIONS:
                        setData();
                        break;
                    case Constants.NOTIFY_CONFIRMED_TRANSACTIONS_ERROR:
                        dismissProgress();
                        break;
                }
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_orders, container,
                false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_confirmed_order_details);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.tv_confirmed_back);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_CONFIRMED_TRANSACTIONS);
        intentFilter.addAction(Constants.NOTIFY_CONFIRMED_TRANSACTIONS_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        RetrofitNetworksCalls calls
                = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.show();
            SharedPreferences sharedPreferences = getActivity()
                    .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                            Context.MODE_PRIVATE);
            int id = 0;
            if (sharedPreferences != null) {
                id = sharedPreferences.getInt(Constants.KEY_ID, 0);
            }
            calls.getConfirmedOrders(getActivity(), id);
        }
        back.setOnClickListener(this);
    }

    private void dismissProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setData() {
        dismissProgress();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {

            List<Order> transactionDetailsLIst = calls.getConfirmedOrdersList();
            if (transactionDetailsLIst != null && !transactionDetailsLIst.isEmpty()) {
                mTransactionDetailsAdapter = new TransactionDetailsAdapter(getActivity(),
                        transactionDetailsLIst,
                        mFragmentListener);
                mTransactionDetailsAdapter
                        .isRequestFromConfirmOrderFragment(true);
                mRecyclerView.setAdapter(mTransactionDetailsAdapter);
            }
        }
    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.mFragmentListener = retailerFragmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirmed_back:
                if (mFragmentListener != null) {
                    mFragmentListener.onBackPress();
                }
                break;
        }
    }
}
