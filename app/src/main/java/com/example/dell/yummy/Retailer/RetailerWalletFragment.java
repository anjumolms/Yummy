package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.UserResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerWalletFragment extends Fragment {

    ProgressDialog progressDialog;
    TextView remainingCoins;
    public RetailerWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_retailer_wallet,
                container, false);
        remainingCoins = view.findViewById(R.id.retailer_remaining_coins);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getUserWalletDetails();
        return view;

    }

    private void getUserWalletDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SharedPreferences sharedpreferences
                = getActivity().getSharedPreferences("RETAILERDETAILS",
                Context.MODE_PRIVATE);

        UserResult userResult = new UserResult();
        userResult.setLoginUsername(sharedpreferences.getString("UserName", null));
        userResult.setLoginPin(sharedpreferences.getInt("UserPin", 0));

        IApiInterface service = retrofit.create(IApiInterface.class);
        Call<UserResult> call = service.userLogin(userResult);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if (response != null) {
                    if (response.code() == 200) {

                        UserResult userResult = response.body();
                        if (userResult != null) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("Wallet", userResult.getUserWallet());
                            editor.commit();
                            progressDialog.dismiss();
                            remainingCoins.setText("" + userResult.getUserWallet());

                        } else if (response.code() == 204) {
                            Toast.makeText(getActivity(), "Invalid email or password",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {

            }
        });

    }


}
