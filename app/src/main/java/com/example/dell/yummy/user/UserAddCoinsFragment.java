package com.example.dell.yummy.user;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAddCoinsFragment extends Fragment implements View.OnClickListener {

    IUserFragmentListener iUserFragmentListener;
    TextView back;
    EditText amount;
    Button proceed;
    int userAmount;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if(intent != null){
                 String action = intent.getAction();
                 switch (action){
                     case Constants.NOTIFY_UPDATE_USER_WALLET:
                         break;

                     case Constants.NOTIFY_UPDATE_USER_WALLET_ERROR:
                         break;

                     case Constants.NOTIFY_WALLET_UPDATED:
                         break;
                     case Constants.NOTIFY_WALLET_UPDATED_ERROR:
                         break;
                 }
             }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_add_coins, container,
                false);


        RetrofitNetworksCalls calls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (calls != null) {
            List<DishesDetails> details = calls.getDishDetailsList();
            List<StoreDetails> storeDetails = calls.getStoreDetailsList();
            if(details != null && !details.isEmpty()){
                int size = details.size();
            }
            if(storeDetails != null && !storeDetails.isEmpty()){
                int size = storeDetails.size();
            }

        }
        back = view.findViewById(R.id.add_coins_back);
        proceed = view.findViewById(R.id.payment_proceed);
        amount = view.findViewById(R.id.user_amount);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_UPDATE_USER_WALLET);
        intentFilter.addAction(Constants.NOTIFY_UPDATE_USER_WALLET_ERROR);
        intentFilter.addAction(Constants.NOTIFY_WALLET_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_WALLET_UPDATED_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        back.setOnClickListener(this);
        proceed.setOnClickListener(this);
        return view;
    }

    public void addListener(IUserFragmentListener iUserFragmentListener) {
        this.iUserFragmentListener = iUserFragmentListener;
    }


    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.add_coins_back:
               if(iUserFragmentListener != null){
                   iUserFragmentListener.onBackPress();
               }
               break;
           case R.id.payment_proceed:
               showChangeLangDialog();
               break;
       }
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_admin_key, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.admin_key);

        dialogBuilder.setTitle("Payment Confirmation");
        dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userAmount = Integer.parseInt(amount.getText().toString().trim());
                if(userAmount > 0){
                    updateUserWallet(userAmount);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateUserWallet(int userAmount) {
        RetrofitNetworksCalls calls
                = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if(calls != null){
            calls.updateUserWallet(getActivity(), userAmount);
        }
    }
}
