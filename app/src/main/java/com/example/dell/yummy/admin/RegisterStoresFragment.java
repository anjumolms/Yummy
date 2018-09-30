package com.example.dell.yummy.admin;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.LocationDetails;
import com.example.dell.yummy.model.RegisterStore;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStoresFragment extends Fragment implements View.OnClickListener {
    private EditText mStoreName;
    private EditText mStoreId;
    private EditText mAccount;
    private EditText mEmail;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mMobile;
    private Button mRegister;
    private ProgressDialog mProgressDialog;
    private Spinner spinner;
    private FloatingActionButton mFabButton;


    public RegisterStoresFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getAction().equals(Constants.NOTIFY_STORE_ADDED)) {
                updateData();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_stores,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mStoreName = view.findViewById(R.id.admin_store_name);
        mStoreId = view.findViewById(R.id.admin_store_id);
        mAccount = view.findViewById(R.id.admin_store_account);
        mEmail = view.findViewById(R.id.admin_store_email);
        mUserName = view.findViewById(R.id.admin_store_user_name);
        mPassword = view.findViewById(R.id.admin_store_password);
        mMobile = view.findViewById(R.id.admin_store_mobile);
        mRegister = view.findViewById(R.id.bt_admin_store_register);
        spinner = view.findViewById(R.id.spinner);
        mFabButton = view.findViewById(R.id.fb_mail);
        mProgressDialog = new ProgressDialog(getActivity());

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_STORE_ADDED);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        ArrayList<String> placesList = new ArrayList<>();
        placesList.add("Select Location");
        if (calls != null) {
            List<LocationDetails> locations = calls.getLocations();
            for (LocationDetails locationDetails : locations) {
                placesList.add(locationDetails.getLocationName());
            }
        }

        ArrayAdapter<String> dataAdapter
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, placesList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        mRegister.setOnClickListener(this);
        mFabButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_admin_store_register:
                registerStore();
                break;
            case R.id.fb_mail:
                sendMail();
                break;
            default:
                break;
        }
    }

    private void sendMail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mEmail.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Yummy");
        i.putExtra(Intent.EXTRA_TEXT, "Please find below updated ," +
                "UserName : " + mUserName.getText().toString() +
                " Password : " + mPassword.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerStore() {
        String userName = mUserName.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();


        if (TextUtils.isEmpty(userName)) {
            mUserName.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("Loading.....");
            mProgressDialog.show();

        }

        int loginPin = Integer.parseInt(strPassword);
        String retailAccount = mAccount.getText().toString().trim();
        int retailNumber = Integer.parseInt(mStoreId.getText().toString().trim());
        int wallet = 0;
        RegisterStore registerStore = new RegisterStore();
        registerStore.setLoginUsername(userName);
        registerStore.setLoginPin(loginPin);
        registerStore.setRetailAccount(retailAccount);
        registerStore.setRetailNumber(retailNumber);
        registerStore.setRetailWallet(wallet);
        registerStore.setRetailName(mStoreName.getText().toString());

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.addStoreDetails(getActivity(), registerStore);
        }

    }

    private void updateData() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mUserName.setText("");
            mStoreName.setText("");
            mEmail.setText("");
            mPassword.setText("");
            mAccount.setText("");
            mStoreId.setText("");
            mMobile.setText("");
        }
    }
}
