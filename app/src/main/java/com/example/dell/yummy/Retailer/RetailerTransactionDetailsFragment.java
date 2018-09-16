package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.TransactionDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerTransactionDetailsFragment extends Fragment {

    private IFragmentListener iFragmentListener;
    private List<TransactionDetails> transactionDetailsList;
    private RecyclerView mRecyclerView;



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

//        TransactionDetailsAdapter adapter = new TransactionDetailsAdapter(getActivity(),
//                transactionDetailsList, iFragmentListener);
//                   mRecyclerView.setAdapter(adapter);




//        Retrofit retrofit = RetrofitClient.getClient();
//
//        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
//        Call<List<TransactionDetails>> call = iApiInterface.getTransactionDetails(2);
//
//        call.enqueue(new Callback<List<TransactionDetails>>() {
//
//
//            @Override
//            public void onResponse(Call<List<TransactionDetails>> call,
//                                   Response<List<TransactionDetails>> response) {
//                if (response != null) {
//                    if (response.code() == 200) {
//                        transactionDetailsList = response.body();
//
//                    } else {
//                        Toast.makeText(getActivity(), response.code()
//                                + response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                if (transactionDetailsList != null) {
//                    TransactionDetailsAdapter adapter = new TransactionDetailsAdapter(getActivity(), transactionDetailsList, iFragmentListener);
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TransactionDetails>> call, Throwable t) {
//
//                Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
//            }
//
//        });


        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_transaction_details);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addListener(IFragmentListener iFragmentListener) {

        this.iFragmentListener = iFragmentListener;

    }
}
