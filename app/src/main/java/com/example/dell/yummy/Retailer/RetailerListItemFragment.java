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
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.user.store.StoreDetailsAdapter;
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
    IFragmentListener IFragmentListener;
    RecyclerView recyclerView;
    List<DishesDetails> retailordishesList;


    public RetailerListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_list_item, container, false);

        recyclerView = view.findViewById(R.id.rv_retailer_list_dish);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        retailordishesList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<DishesDetails>> call = iApiInterface
                .getStoreMenu(2);

        call.enqueue(new Callback<List<DishesDetails>>() {


            @Override
            public void onResponse(Call<List<DishesDetails>> call,
                                   Response<List<DishesDetails>> response) {
                if (response != null) {
                    if (response.code() == 200) {
                        retailordishesList = response.body();
                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }
                if (retailordishesList != null) {
                    RetailerListItemAdapter adapter = new RetailerListItemAdapter(getActivity(), retailordishesList);
                    recyclerView.setAdapter(adapter);
                }

            }


            @Override
            public void onFailure(Call<List<DishesDetails>> call, Throwable t) {

                Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
            }

        });


        return view;

    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.IFragmentListener = iFragmentListener;
    }
}
