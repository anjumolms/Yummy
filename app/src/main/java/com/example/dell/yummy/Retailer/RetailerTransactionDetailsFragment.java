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
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.user.store.StoreDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerTransactionDetailsFragment extends Fragment {

    IFragmentListener iFragmentListener;
    List<TransactionDetails> transactionDetailsList;
    RecyclerView recyclerView;



    public RetailerTransactionDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_retailer_transaction_details, container, false);
        recyclerView = view.findViewById(R.id.rv_transaction_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionDetailsList = new ArrayList<>();

        transactionDetailsList.add(
                new TransactionDetails(
                        166,
                        185555,
                        70
                ));


        transactionDetailsList.add(
                new TransactionDetails(
                        1553553,
                        3666331,
                        70
                ));

        transactionDetailsList.add(
                new TransactionDetails(
                        123535,
                        3663,
                        70
                ));

        transactionDetailsList.add(
                new TransactionDetails(
                        138388,
                        3553535,
                        70
                ));

       TransactionDetailsAdapter adapter = new TransactionDetailsAdapter(getActivity(),transactionDetailsList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void addListener(IFragmentListener iFragmentListener) {

        this.iFragmentListener = iFragmentListener;

    }
}
