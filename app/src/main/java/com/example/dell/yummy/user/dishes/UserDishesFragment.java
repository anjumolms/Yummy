package com.example.dell.yummy.user.dishes;


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
import com.example.dell.yummy.user.store.UserStoresAdapter;
import com.example.dell.yummy.webservice.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitClient;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDishesFragment extends Fragment {

    List<DishesDetails> dishesList;
    RecyclerView recyclerView;
    IFragmentListener miFragmentListener;


    public UserDishesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_dishes, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dishesList = new ArrayList<>();
//
//        List<DishesDetails> dummyStoreList = new ArrayList<>();
//        DishesDetails storeDetails1 = new DishesDetails();
//        DishesDetails storeDetails2 = new DishesDetails();
//        DishesDetails storeDetails3 = new DishesDetails();
//        DishesDetails storeDetails4 = new DishesDetails();
//        DishesDetails storeDetails5 = new DishesDetails();
//
//
//        storeDetails1.setItemName("PATHIRI");
//        storeDetails1.setItemPrice(50);
//
//        storeDetails2.setItemName("UNNIYAPPAM");
//        storeDetails2.setItemPrice(20);
//
//        storeDetails3.setItemName("LADDU");
//        storeDetails3.setItemPrice(25);
//
//        storeDetails4.setItemName("JILLEBI");
//        storeDetails4.setItemPrice(10);
//
//        storeDetails5.setItemName("PAYASAM");
//        storeDetails5.setItemPrice(30);
//
//
//
//        dummyStoreList.add(storeDetails1);
//        dummyStoreList.add(storeDetails2);
//        dummyStoreList.add(storeDetails3);
//        dummyStoreList.add(storeDetails4);
//        dummyStoreList.add(storeDetails5);
//
//        UserDishesAdapter adapter = new UserDishesAdapter(getActivity(),
//                dummyStoreList, miFragmentListener);
//        recyclerView.setAdapter(adapter);
//
        Retrofit retrofit = RetrofitClient.getClient();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<DishesDetails>> call = iApiInterface.getallMenu();


        call.enqueue(new Callback<List<DishesDetails>>() {
            @Override
            public void onResponse(Call<List<DishesDetails>> call,
                                   Response<List<DishesDetails>> response) {

                if (response != null) {
                    if (response.code() == 200) {
                        dishesList = response.body();

                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                if(dishesList != null){
                    UserDishesAdapter adapter = new UserDishesAdapter(getActivity(), dishesList, miFragmentListener);

                    //setting adapter to recyclerview
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

    public void addListener(IFragmentListener miFragmentListener) {
        this.miFragmentListener = miFragmentListener;

    }
}


