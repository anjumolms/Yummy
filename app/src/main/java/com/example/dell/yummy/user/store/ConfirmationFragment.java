package com.example.dell.yummy.user.store;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.UserResult;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends
        Fragment implements View.OnClickListener {

    private IUserFragmentListener mFragmentListener;
    private List<DishesDetails> dishesDetails;
    private ConfirmationAdapter mConfirmationAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewTotal;
    private TextView mWallet;
    private int mTotal = 0;
    private LinearLayout linearLayout;
    private int orderitemcount = 0;
    private Button mConfirmbutton;
    private String mMenuitem = "";
    private int retailid;
    private ProgressDialog mProgressDialog;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_USER_CONFIRM_ORDER:
                        getUserWalletDetails();
                        break;
                    case Constants.NOTIFY_WALLET_UPDATED:
                        showWalletUpdation();
                        break;
                    case Constants.NOTIFY_USER_CONFIRM_ORDER_ERROR:
                        stopProgress();
                        break;
                    default:
                        break;
                }
            }
        }
    };

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
            int dishCount = dishdetail.getCounter();
            if (dishCount > 0) {
                mTotal = mTotal + (dishdetail.getItemPrice() * dishCount);
                orderitemcount = orderitemcount + dishCount;
                for (int i = 0; i < dishCount; i++) {

                    if (mMenuitem.equalsIgnoreCase("")) {
                        mMenuitem = "" + dishdetail.getMenuId();
                    } else {
                        mMenuitem = mMenuitem + "," + dishdetail.getMenuId();
                    }
                }
                retailid = dishdetail.getRetailId();
            }
        }
        mTextViewTotal.setText(" " + mTotal);
    }

    private void stopProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_confirmation_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextViewTotal = view.findViewById(R.id.tv_total_price);
        mConfirmbutton = view.findViewById(R.id.bt_confirm);
        mProgressDialog = new ProgressDialog(getActivity());
        linearLayout = view.findViewById(R.id.ll_wallet_updation);
        mWallet = view.findViewById(R.id.user_wallet);
        mTotal = 0;
        orderitemcount = 0;
        mMenuitem = "";
        retailid = 0;
        linearLayout.setVisibility(View.GONE);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_USER_CONFIRM_ORDER);
        intentFilter.addAction(Constants.NOTIFY_WALLET_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_USER_CONFIRM_ORDER_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
    }


    public void addListener(IUserFragmentListener iUserFragmentListener) {
        this.mFragmentListener = iUserFragmentListener;
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
        SharedPreferences sharedpreferences
                = getActivity()
                .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                        Context.MODE_PRIVATE);
        int wallet = sharedpreferences.getInt(Constants.KEY_WALLET, 0);

        if (wallet >= mTotal) {

            final Order orderDetails = new Order();

            int userid = sharedpreferences.getInt(Constants.KEY_ID, 0);

            orderDetails.setUser_id(userid);
            orderDetails.setRetail_id(retailid);
            orderDetails.setOrder_item_count(orderitemcount);
            orderDetails.setOrder_value(mTotal);
            orderDetails.setOrder_items_string(mMenuitem);
            if (isNetworkAvailable()) {
                RetrofitNetworksCalls calls = DataSingleton
                        .getInstance().getRetrofitNetworksCallsObject();
                if (calls != null) {
                    calls.confirmOrder(getActivity(), orderDetails);
                }
            } else {
                if (mFragmentListener != null) {
                    mFragmentListener.showSnackBar();
                }
            }


        } else {
            Toast.makeText(getActivity(),
                    "Insufficient Balance", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUserWalletDetails() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getUserWalletDetails(getActivity());
        }
    }

    private void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
        }

    }

    private void showWalletUpdation() {
        SharedPreferences sharedpreferences
                = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);
        int wallet = sharedpreferences.getInt(Constants.KEY_WALLET, 0);
        mWallet.setText("" + wallet);
        linearLayout.setVisibility(View.VISIBLE);
        mConfirmbutton.setVisibility(View.GONE);
        mProgressDialog.dismiss();
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

}
