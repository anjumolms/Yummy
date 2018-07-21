package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStoresFragment extends Fragment {
    RecyclerView recyclerView;
    IFragmentListener miFragmentListener;
    List<StoreDetails> StoreList;


    public UserStoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_stores, container, false);
        recyclerView = view.findViewById(R.id.rv_stores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<StoreDetails>> call = iApiInterface.getStores();

        call.enqueue(new Callback<List<StoreDetails>>() {

            @Override
            public void onResponse(Call<List<StoreDetails>> call,
                                   Response<List<StoreDetails>> response) {

                if (response != null) {
                    if (response.code() == 200) {
                        StoreList = response.body();
                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                if(StoreList != null){

                    UserStoresAdapter adapter = new UserStoresAdapter(getActivity(), StoreList, miFragmentListener);

                    //setting adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<StoreDetails>> call, Throwable t) {
                Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void addListener(IFragmentListener miFragmentListener) {
        this.miFragmentListener = miFragmentListener;
    }
}
