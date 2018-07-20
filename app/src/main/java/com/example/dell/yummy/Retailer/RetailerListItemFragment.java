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
public class RetailerListItemFragment extends Fragment {
  IFragmentListener IFragmentListener;
  RecyclerView recyclerView;
  ArrayList<DishesDetails> retailordishesList;


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

        retailordishesList.add(new DishesDetails
                (0,"Pazham","jjj",0,34)) ;
        retailordishesList.add(new DishesDetails
                (0,"Pazham","jjj",0,34)) ;
        retailordishesList.add(new DishesDetails
                (0,"Pazham","jjj",0,34)) ;
        retailordishesList.add(new DishesDetails
                (0,"Pazham","jjj",0,34)) ;

        RetailerListItemAdapter adapter = new RetailerListItemAdapter(getActivity(),retailordishesList);
        recyclerView.setAdapter(adapter);
        return view;

    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.IFragmentListener = iFragmentListener;
    }
}
