package com.example.dell.yummy.admin;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.IMainViewListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdminFragment extends Fragment implements View.OnClickListener {
    private EditText mMbole;
    private EditText mPassword;
    private Button mRegister;
    private IAdminFragmentListener iAdminFragmentListener;
    private ProgressDialog progressDialog;
    private TextView textView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_ADD_ADMIN:
                        dismssProgress();
                        break;
                    case Constants.NOTIFY_ADD_ADMIN_ERROR:
                        dismssProgress();
                        break;
                }
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_admin, container,
                false);
        initViews(view);
        mRegister.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mMbole = view.findViewById(R.id.add_admin_mobile);
        mPassword = view.findViewById(R.id.add_admin_password);
        mRegister = view.findViewById(R.id.btn_add_admin_register);
        textView = view.findViewById(R.id.id_add_admins);
        progressDialog = new ProgressDialog(getActivity());

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_ADD_ADMIN);
        intentFilter.addAction(Constants.NOTIFY_ADD_ADMIN_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        textView.setOnClickListener(this);
    }

    public void addListener(IAdminFragmentListener iAdminFragmentListener) {
        this.iAdminFragmentListener = iAdminFragmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_admin_register:
                validateUser();
                break;
            case R.id.id_add_admins:
                if(iAdminFragmentListener != null){
                    iAdminFragmentListener.onBackPress();
                }
                break;
        }
    }

    private void validateUser() {
        String strUserName = mMbole.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(strUserName)) {
            mMbole.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        } else if (strUserName.length() < 10 || strUserName.length() > 10) {
            mMbole.setError("Please enter a valid mobile number");
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        progressDialog.setMessage("Loading......");
        progressDialog.show();

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.addAdmin(getActivity(), strUserName, strPassword);
        }
    }

    private void dismssProgress() {
        mMbole.setText("");
        mPassword.setText("");
        mMbole.requestFocus();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
