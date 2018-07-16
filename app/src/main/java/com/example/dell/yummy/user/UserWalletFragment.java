package com.example.dell.yummy.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserWalletFragment extends Fragment {

    IFragmentListener iFragmentListener;
    private int coins;
    private TextView mUserCoin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        if(bundle != null) {
            coins = bundle.getInt("Key2");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_user_wallet, container, false);
      mUserCoin = view.findViewById(R.id.tv_user_coin);
      mUserCoin.setText("Remaining Coins : " + coins);

        return view;
    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }
}
