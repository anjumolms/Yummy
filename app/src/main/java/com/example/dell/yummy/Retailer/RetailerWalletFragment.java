package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.UserResult;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerWalletFragment extends Fragment implements View.OnClickListener {

    ProgressDialog progressDialog;
    TextView remainingCoins;
    private IRetailerFragmentListener mFragmentListener;
    private TextView back;

    public RetailerWalletFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_WALLET_UPDATED:
                        updateData();
                        break;
                    case Constants.NOTIFY_WALLET_UPDATED_ERROR:
                        stopProgress();
                        break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_wallet,
                container, false);
        initViews(view);


        return view;

    }

    private void initViews(View view) {
        remainingCoins = view.findViewById(R.id.retailer_remaining_coins);
        back = view.findViewById(R.id.wallet_back);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //getUserWalletDetails();
        if (isNetworkAvailable()) {
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                calls.getUserWalletDetails(getActivity());
            }
        } else {
            if (mFragmentListener != null) {
                mFragmentListener.showSnackBar();
            }
        }


        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_WALLET_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_WALLET_UPDATED_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        back.setOnClickListener(this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager ConnectionManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            return true;
        } else {
            return false;
        }

    }

    private void stopProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void updateData() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        SharedPreferences sharedpreferences
                = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);
        if (sharedpreferences != null) {
            int wallet = sharedpreferences
                    .getInt(Constants.KEY_WALLET, 0);
            remainingCoins.setText("Wallet : " + wallet);

        }
    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.mFragmentListener = retailerFragmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wallet_back:
                if(mFragmentListener != null){
                    mFragmentListener.onBackPress();
                }
                break;
        }
    }
}
