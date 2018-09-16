package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerListItemFragment extends Fragment {
    private IFragmentListener mFragmentListener;
    private RecyclerView mRecyclerView;
    private List<DishesDetails> mRetailordishesList;


    public RetailerListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_list_item, container, false);
        initViews(view);

        mRetailordishesList = new ArrayList<>();
        getDishesList();
        return view;

    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_retailer_list_dish);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.mFragmentListener = iFragmentListener;
    }

    public void getDishesList() {
        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            //TODO : Hardcoded Store Id.
            Call<List<DishesDetails>> call = iApiInterface
                    .getStoreMenu(2);

            call.enqueue(new Callback<List<DishesDetails>>() {
                @Override
                public void onResponse(Call<List<DishesDetails>> call,
                                       Response<List<DishesDetails>> response) {
                    if (response != null && response.code() == 200) {
                        mRetailordishesList = response.body();

                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                    if (mRetailordishesList != null) {
                        RetailerListItemAdapter adapter = new RetailerListItemAdapter(getActivity(),
                                mRetailordishesList);
                        mRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<DishesDetails>> call, Throwable t) {
                    Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}
