package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.User;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefundFragment extends Fragment implements View.OnClickListener {

    private EditText serachNumber;
    private Button mRefund;
    private TextView mUserName;
    private TextView mUserEmail;
    private ProgressDialog mProgressDialog;
    private EditText amount;
    private User user;
    private SharedPreferences sharedPreferences;
    private TextView back;
    IRetailerFragmentListener mFragmentListener;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Constants.NOTIFY_REFUND)) {

                    updateData();
                }
                if (intent.getAction().equals(Constants.NOTIFY_REFUND_ERROR)) {
                    stopProgress();
                }

                if (intent.getAction().equals(Constants.NOTIFY_GET_USER_BY_NUMBER)) {
                    showUserDetails();
                }
                if (intent.getAction().equals(Constants.NOTIFY_GET_USER_BY_NUMBER_ERROR)) {
                    stopProgress();
                }
            }

        }
    };

    public RefundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_refund,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        serachNumber = view.findViewById(R.id.input_user_number);
        mRefund = view.findViewById(R.id.bt_refund);
        mUserName = view.findViewById(R.id.refund_user_name);
        mUserEmail = view.findViewById(R.id.refund_user_email);
        amount = view.findViewById(R.id.refund_user_amount);
        back = view.findViewById(R.id.id_refund_back);
        mRefund.setVisibility(View.GONE);
        sharedPreferences
                = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);
        mProgressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_REFUND);
        intentFilter.addAction(Constants.NOTIFY_REFUND_ERROR);
        intentFilter.addAction(Constants.NOTIFY_GET_USER_BY_NUMBER);
        intentFilter.addAction(Constants.NOTIFY_GET_USER_BY_NUMBER_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

        serachNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (serachNumber.getRight() - serachNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        RetrofitNetworksCalls calls = DataSingleton
                                .getInstance().getRetrofitNetworksCallsObject();
                        String number = serachNumber.getText().toString().trim();

                        if (calls != null && number != null && !number.isEmpty()) {
                            mProgressDialog.setMessage("Loading.......");
                            mProgressDialog.show();
                            mProgressDialog.setCancelable(false);
                            calls.getUsersByNumber(getActivity(), number);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        mRefund.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.mFragmentListener = retailerFragmentListener;
    }

    private void stopProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void updateData() {
       stopProgress();
       serachNumber.setText("");
       amount.setText("");
       mRefund.setVisibility(View.GONE);
       mUserName.setVisibility(View.GONE);
       mUserEmail.setVisibility(View.GONE);
       amount.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_refund:
                callRefundApi();
                break;
            case R.id.id_refund_back:
                if (mFragmentListener != null) {
                    mFragmentListener.onBackPress();

                }
                break;
            default:
                break;
        }
    }

    private void showUserDetails() {
        stopProgress();
        RetrofitNetworksCalls calls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (calls != null) {
            user = calls.getUserDetailsByNuber();
            if (user != null) {
                mUserName.setText(user.getUser_name());
                mUserEmail.setText(user.getUser_email());
                mUserEmail.setVisibility(View.VISIBLE);
                mUserName.setVisibility(View.VISIBLE);
                amount.setVisibility(View.VISIBLE);
                mRefund.setVisibility(View.VISIBLE);
            }

        }
    }

    private void callRefundApi() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null && user != null) {
            String number = amount.getText().toString().trim();
            int userId = user.getUser_id();
            int retail = 0;
            if (sharedPreferences != null) {
                retail = sharedPreferences.getInt(Constants.KEY_ID, 0);
            }
            mProgressDialog.setMessage("Loading.......");
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
            calls.callRefundApi(getActivity(), Integer.parseInt(number),userId,retail);
        }
    }
}
