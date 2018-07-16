package com.example.dell.yummy.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAddCoinsFragment extends Fragment {

    IFragmentListener iFragmentListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_add_coins, container,
                false);


        return view;
    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }
}
