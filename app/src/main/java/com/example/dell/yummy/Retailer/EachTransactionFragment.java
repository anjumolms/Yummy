package com.example.dell.yummy.Retailer;


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
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EachTransactionFragment extends Fragment implements View.OnClickListener {
    private TextView mUserId;
    private TextView mTotal;
    private Order mTransactionDetails;
    private RecyclerView mRecyclerView;
    private Button mConfirmOrder;
    private ProgressDialog mProgressDialog;
    private TextView orderStatus;
    private TextView orederId;
    private TextView date;
    private TextView back;
    IRetailerFragmentListener iRetailerFragmentListener;


    public EachTransactionFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_TRANSACTION_ORDER)) {
                updateData();
            }
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_UPDATE_DELIVERY_ERROR)) {
            }
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_UPDATE_DELIVERY)) {
                setVisibilityOfConfirmButton();
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_each_transaction,
                container, false);
        initViews(view);
        return view;
    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.iRetailerFragmentListener = retailerFragmentListener;
    }


    private void initViews(View view) {
        mUserId = view.findViewById(R.id.tv_user_id_each_transaction_details);
        mTotal = view.findViewById(R.id.tv_total_each_transaction_fragment);
        mRecyclerView = view.findViewById(R.id.rv_each_transaction_details);
        mConfirmOrder = view.findViewById(R.id.bt_confirm_each_transcation_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressDialog = new ProgressDialog(getActivity());
        orderStatus = view.findViewById(R.id.tv_each_transaction_order_id);
        date = view.findViewById(R.id.tv_order_date);
        orederId = view.findViewById(R.id.tv_order_id);
        back = view.findViewById(R.id.tv_order_back);


        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_UPDATE_DELIVERY);
        intentFilter.addAction(Constants.NOTIFY_UPDATE_DELIVERY_ERROR);
        intentFilter.addAction(Constants.NOTIFY_TRANSACTION_ORDER);
        intentFilter.addAction(Constants.NOTIFY_TRANSACTION_ORDER_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        mConfirmOrder.setOnClickListener(this);
        back.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (mTransactionDetails != null) {
            mUserId.setText(" " + mTransactionDetails.getUser_id());
            mTotal.setText(" " + mTransactionDetails.getOrder_value());
            date.setText("" + mTransactionDetails.getOrder_date());
            orderStatus.setText("" + mTransactionDetails.getOrder_status());
            orederId.setText("" + mTransactionDetails.getOrder_id());
            int orderId = mTransactionDetails.getOrder_id();
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                calls.getTransactionOrders(getActivity(), orderId);
                mProgressDialog.setMessage("Loading....");
                mProgressDialog.show();
            }
        }
    }

    private void updateData() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                Order order = calls.getOrderInfo();
                if (order != null) {
                    List<DishesDetails> details = order.getOrder_items();
                    EachTransactionAdapter eachTransactionAdapter
                            = new EachTransactionAdapter(getActivity(), details);
                    mRecyclerView.setAdapter(eachTransactionAdapter);
                }
            }
        }
    }

    public void listPosition(Order details) {
        mTransactionDetails = details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm_each_transcation_fragment:
                RetrofitNetworksCalls networksCalls = DataSingleton
                        .getInstance().getRetrofitNetworksCallsObject();

                if (networksCalls != null && mTransactionDetails != null) {
                    networksCalls.addOrderConfirmedTransaction(mTransactionDetails);
                    networksCalls.updateDelivery(mTransactionDetails.getOrder_id(),
                            getActivity());
                }

                break;
            case R.id.tv_order_back:
                if(iRetailerFragmentListener != null){
                    iRetailerFragmentListener.onBackPress();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RetrofitNetworksCalls networksCalls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (networksCalls != null) {
            networksCalls.updateOrderInfo();
        }
    }

    private void setVisibilityOfConfirmButton() {
        mConfirmOrder.setVisibility(View.GONE);
    }
}
