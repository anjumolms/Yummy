package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.TransactionDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EachTransactionFragment extends Fragment implements View.OnClickListener {
    private int mListPosition = -1;
    private TextView mUserId;
    private TextView mTotal;
    private TransactionDetails mTransactionDetails;
    private RecyclerView mRecyclerView;
    private Button mConfirmOrder;

    public EachTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_each_transaction,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mUserId = view.findViewById(R.id.tv_user_id_each_transaction_details);
        mTotal = view.findViewById(R.id.tv_total_each_transaction_fragment);
        mRecyclerView = view.findViewById(R.id.rv_each_transaction_details);
        mConfirmOrder = view.findViewById(R.id.bt_confirm_each_transcation_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mListPosition != -1) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {

                List<TransactionDetails> transactionList
                        = retrofitNetworksCalls.getTransactionList();
                if (transactionList != null && !transactionList.isEmpty()) {
                    mTransactionDetails = transactionList.get(mListPosition);
                }
            }

        }
        mConfirmOrder.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (mTransactionDetails != null) {
            mUserId.setText(" " + mTransactionDetails.getUserId());
            mTotal.setText(" " + mTransactionDetails.getOrderValue());
        }
    }

    public void listPosition(int position) {
        mListPosition = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm_each_transcation_fragment:
                RetrofitNetworksCalls networksCalls = DataSingleton
                        .getInstance().getRetrofitNetworksCallsObject();
                if (networksCalls != null) {
                    networksCalls.addOrderConfirmedTransaction(mTransactionDetails);
                }
                break;
            default:
                break;
        }
    }
}
