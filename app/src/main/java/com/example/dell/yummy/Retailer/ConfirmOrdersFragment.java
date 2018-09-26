package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.TransactionDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrdersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TransactionDetailsAdapter mTransactionDetailsAdapter;
    private IRetailerFragmentListener mFragmentListener;


    public ConfirmOrdersFragment() {
        // Required empty public constructor
    }


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

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if(calls != null){
          List<TransactionDetails> transactionDetailsLIst = calls.getConfirmOrderList();
          if(transactionDetailsLIst != null && !transactionDetailsLIst.isEmpty()){
              mTransactionDetailsAdapter = new TransactionDetailsAdapter(getActivity(),transactionDetailsLIst,
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

}
