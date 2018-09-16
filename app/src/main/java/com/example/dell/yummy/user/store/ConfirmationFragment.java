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
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment implements View.OnClickListener {

    private IFragmentListener mFragmentListener;
    private List<DishesDetails> dishesDetails;
    private ConfirmationAdapter mConfirmationAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewTotal;
    private int mTotal = 0;

    private double ordervalue = 0;
    private int orderitemcount = 0;
    private Button mConfirmbutton;
    private int userid;
    private String mMenuitem = "";
    private int retailid;
    private ProgressDialog mProgressDialog;

    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container,
                false);
        initViews(view);
        showDishDetails();
        mConfirmbutton.setOnClickListener(this);
        return view;
    }

    private void showDishDetails() {
        if (dishesDetails != null) {
            mConfirmationAdapter = new ConfirmationAdapter(getActivity(), dishesDetails);
            //setting adapter to recyclerview
            mRecyclerView.setAdapter(mConfirmationAdapter);
            setTotalPrice();
        }
    }

    private void setTotalPrice() {
        for (DishesDetails dishdetail : dishesDetails) {
            if (dishdetail.getCounter() > 0) {
                mTotal = mTotal + (dishdetail.getItemPrice() * dishdetail.getCounter());
                orderitemcount = orderitemcount + dishdetail.getCounter();
                if (mMenuitem.equalsIgnoreCase("")) {
                    mMenuitem = "" + dishdetail.getMenuId();
                } else {
                    mMenuitem = mMenuitem + "," + dishdetail.getMenuId();
                }
                retailid = dishdetail.getRetailId();
            }
        }
        mTextViewTotal.setText(" " + mTotal);
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_confirmation_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextViewTotal = view.findViewById(R.id.tv_total_price);
        mConfirmbutton = view.findViewById(R.id.bt_confirm);
    }

    private void getUserWalletDetails() {
        final SharedPreferences sharedpreferences
                = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);

        UserResult userResult = new UserResult();
        userResult.setLoginUsername(sharedpreferences.getString(Constants.KEY_USER_NAME,
                null));
        userResult.setLoginPin(sharedpreferences.getInt(Constants.KEY_LOGIN_PIN,
                0));

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<UserResult> call = service.userLogin(userResult);
            call.enqueue(new Callback<UserResult>() {
                @Override
                public void onResponse(Call<UserResult> call,
                                       Response<UserResult> response) {
                    if (response != null && response.code() == 200) {

                        UserResult userResult = response.body();
                        if (userResult != null) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt(Constants.KEY_WALLET,
                                    userResult.getUserWallet());
                            editor.commit();
                            mProgressDialog.dismiss();
                            if (mFragmentListener != null) {
                                mFragmentListener.addFragment(Constants.SCREEN_ORDER_SUCCESSFULL);
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

                @Override
                public void onFailure(Call<UserResult> call, Throwable t) {

                }
            });

        }

    }


    public void addListener(IFragmentListener iFragmentListener) {
        this.mFragmentListener = iFragmentListener;
    }

    public void setConfirmationDetails(List<DishesDetails> dishesDetails) {
        this.dishesDetails = dishesDetails;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                confirmOrder();
                break;
            default:
                break;
        }
    }

    private void confirmOrder() {
        if (mFragmentListener != null) {
            //TODO: Get user id from SharedPreferance.
        }
        showProgress();
        final Order orderDetails = new Order();
        orderDetails.setUser_id(userid);
        orderDetails.setRetail_id(retailid);
        orderDetails.setOrder_item_count(orderitemcount);
        orderDetails.setOrder_value(mTotal);
        orderDetails.setOrder_items_string(mMenuitem);


        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {

            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<Order> call = iApiInterface
                    .getOrderDetails(orderDetails);//how to use userid defined in userhome ctivity here

            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (response != null && response.code() == 200) {
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

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    Toast.makeText(getActivity(), call.toString()
                            + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }
}
