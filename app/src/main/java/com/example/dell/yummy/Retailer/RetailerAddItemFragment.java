package com.example.dell.yummy.Retailer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerAddItemFragment extends Fragment {

    IFragmentListener iFragmentListener;


    public RetailerAddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_add_item, container, false);
        return view;

    }


    public void addListener( IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }
}
