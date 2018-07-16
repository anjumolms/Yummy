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

import java.util.ArrayList;
import java.util.List;

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

        dishesList.add(
                new DishesDetails(
                        1,
                        "chikn",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "skjshd)",
                        "14 inch, Gray, 1.659 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "gdfhd)",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "dfd",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

        dishesList.add(
                new DishesDetails(
                        1,
                        "dfgrd",
                        "13.3 inch, Silver, 1.35 kg",
                        4.3,
                        60000));

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
