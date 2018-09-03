package com.example.dell.yummy.user.store;


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
public class OrderSuccessfullFragment extends Fragment {

    IFragmentListener iFragmentListener;

    public OrderSuccessfullFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_successfull, container, false);
    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }

}
