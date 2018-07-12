package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserViewListener;
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.user.dishes.UserDishesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment {

    List<DishesDetails> dishesList;
    RecyclerView recyclerView;
    IUserViewListener miUserViewListener;

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

        dishesList.add(
                new DishesDetails(
                        1,
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                        "14 inch, Gray, 1.659 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        UserDishesAdapter adapter = new UserDishesAdapter(getActivity(), dishesList,miUserViewListener);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void addListener(IUserViewListener mIUserViewListener) {
        miUserViewListener = mIUserViewListener;

    }

}
