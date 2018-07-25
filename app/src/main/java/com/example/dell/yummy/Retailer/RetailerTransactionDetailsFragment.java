package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitClient;
import com.example.dell.yummy.webservice.TransactionDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
         View view = inflater.inflate(R.layout.fragment_retailer_transaction_details,
                 container, false);
        recyclerView = view.findViewById(R.id.rv_transaction_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //transactionDetailsList = new ArrayList<>();

        Retrofit retrofit = RetrofitClient.getClient();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<TransactionDetails>> call = iApiInterface.getTransactionDetails(2);

        call.enqueue(new Callback<List<TransactionDetails>>() {


            @Override
            public void onResponse(Call<List<TransactionDetails>> call,
                                   Response<List<TransactionDetails>> response) {
                if (response != null) {
                    if (response.code() == 200) {
                        transactionDetailsList = response.body();

                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                if (transactionDetailsList != null) {
                    TransactionDetailsAdapter adapter = new TransactionDetailsAdapter(getActivity(), transactionDetailsList, iFragmentListener);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TransactionDetails>> call, Throwable t) {

                Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
            }

        });


        return view;
    }

    public void addListener(IFragmentListener iFragmentListener) {

        this.iFragmentListener = iFragmentListener;

    }
}
