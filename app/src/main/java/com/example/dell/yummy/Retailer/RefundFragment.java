package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
        mUserEmail = view.findViewById(R.id.refund_user_name);
        mUserName = view.findViewById(R.id.refund_user_email);
        mProgressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_REFUND);
        intentFilter.addAction(Constants.NOTIFY_REFUND_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

//        serachNumber.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    if(event.getRawX() >= (serachNumber.getRight() - serachNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//
//                        Toast.makeText(ChatActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        mRefund.setOnClickListener(this);
    }

    private void stopProgress() {
    }

    private void updateData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_refund:
                String number = serachNumber.getText().toString().trim();
                callRefundApi(number);
                break;
            default:
                break;
        }
    }

    private void callRefundApi(String number) {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if(calls != null && number != null && !number.isEmpty()){
            mProgressDialog.setMessage("Loading.......");
            mProgressDialog.show();
            //calls.callRefundApi(number);
        }
    }
}
