package com.example.dell.yummy.user.store;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.Order;
import com.example.dell.yummy.webservice.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    IFragmentListener iFragmentListener;
    List<DishesDetails> dishesDetails;
    ConfirmationAdapter confirmationAdapter;
    RecyclerView recyclerView;
    TextView textViewTotal;
    private int total = 0;
    private double ordervalue = 0;
    private int orderitemcount = 0;
    Button confirmbutton;
    private int userid;
    String menuitem = "";
    private int retailid;
     ProgressDialog progressDialog;

    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container,
                false);
        recyclerView = view.findViewById(R.id.rv_confirmation_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        textViewTotal = view.findViewById(R.id.tv_total_price);
        confirmbutton = view.findViewById(R.id.bt_confirm);
        if (dishesDetails != null) {

            confirmationAdapter = new ConfirmationAdapter(getActivity(), dishesDetails);
            //setting adapter to recyclerview
            recyclerView.setAdapter(confirmationAdapter);

            for (DishesDetails dishdetail : dishesDetails) {
                if (dishdetail.getCounter() > 0) {
                    total = total + (dishdetail.getItemPrice() * dishdetail.getCounter());
                    orderitemcount = orderitemcount + dishdetail.getCounter();
                    if (menuitem.equalsIgnoreCase("")) {
                        menuitem = "" + dishdetail.getMenuId();
                    } else {
                        menuitem = menuitem + "," + dishdetail.getMenuId();
                    }
                    retailid = dishdetail.getRetailId();
                }
            }
            textViewTotal.setText(" " + total);

        }

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iFragmentListener != null) {
                    userid = iFragmentListener.getUserId();
                }
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                final Order orderDetails = new Order();
                orderDetails.setUser_id(userid);
                orderDetails.setRetail_id(retailid);
                orderDetails.setOrder_item_count(orderitemcount);
                orderDetails.setOrder_value(total);
                orderDetails.setOrder_items_string(menuitem);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IApiInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
                Call<Order> call = iApiInterface
                        .getOrderDetails(orderDetails);//how to use userid defined in userhome ctivity here

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response != null) {
                            if (response.code() == 200) {

                                Order result = response.body();
                                //TODO add order success variable
                                if (result != null
                                        && result.getOrder_status()
                                        .equalsIgnoreCase("ORDER_SUCCESSFULL")) {
                                    Toast.makeText(getActivity(),
                                            "Order successfull", Toast.LENGTH_SHORT).show();
                                    getUserWalletDetails();

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Toast.makeText(getActivity(), call.toString()
                                + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    private void getUserWalletDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SharedPreferences sharedpreferences
                = getActivity().getSharedPreferences("USERDETAILS", Context.MODE_PRIVATE);

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
                            if (iFragmentListener != null) {
                                iFragmentListener.addFragment(Constants.SCREEN_ORDER_SUCCESSFULL);
                            }

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


    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }

    public void setConfirmationDetails(List<DishesDetails> dishesDetails) {
        this.dishesDetails = dishesDetails;
    }
}
