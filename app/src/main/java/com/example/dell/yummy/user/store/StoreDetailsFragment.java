package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.R;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment {

    List<DishesDetails> dishesList;
    RecyclerView recyclerView;
    IFragmentListener miFragmentListener;

    public StoreDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);
        recyclerView = view.findViewById(R.id.rv_storedetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dishesList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<DishesDetails>> call = iApiInterface.getStoreDishes();







        // UserDishesAdapter adapter = new UserDishesAdapter(getActivity(), dishesList,miUserViewListener);
        StoreDetailsAdapter adapter = new StoreDetailsAdapter(getActivity(),dishesList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void addListener(IFragmentListener mIFragmentListener) {
        miFragmentListener = mIFragmentListener;

    }

}
