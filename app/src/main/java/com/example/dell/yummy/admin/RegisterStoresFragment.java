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
import android.widget.TextView;
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
    private EditText mStorePoc;
    private EditText mStoreId;
    private EditText mAccount;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mMobile;
    private Button mRegister;
    private ProgressDialog mProgressDialog;
    private Spinner spinner;
    private int location_id;
    private TextView back;
    private IAdminFragmentListener iAdminFragmentListener;
    ArrayList<String> placesList;
    List<LocationDetails> locations;


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
        mStorePoc = view.findViewById(R.id.admin_store_poc);
        mStoreId = view.findViewById(R.id.admin_store_id);
        mAccount = view.findViewById(R.id.admin_store_account);
        mEmail = view.findViewById(R.id.admin_store_email);
        mPassword = view.findViewById(R.id.admin_store_password);
        mMobile = view.findViewById(R.id.admin_store_mobile);
        mRegister = view.findViewById(R.id.bt_admin_store_register);
        spinner = view.findViewById(R.id.spinner);
        back = view.findViewById(R.id.tv_register_back);
        mProgressDialog = new ProgressDialog(getActivity());

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_STORE_ADDED);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);


        setSpinner();

        back.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }

    private void setSpinner() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        placesList = new ArrayList<>();
        placesList.add("Select Location");
        if (calls != null) {
            locations = calls.getLocations();
            for (LocationDetails locationDetails : locations) {
                placesList.add(locationDetails.getLocationName());
            }
        }

        ArrayAdapter<String> dataAdapter
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, placesList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R
                .layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public void addListener(IAdminFragmentListener iAdminFragmentListener) {

        this.iAdminFragmentListener = iAdminFragmentListener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_admin_store_register:
                registerStore();
                break;
            case R.id.tv_register_back:
                if(iAdminFragmentListener != null){
                    iAdminFragmentListener.onBackPress();
                }
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
        i.putExtra(Intent.EXTRA_TEXT, "Please find below details" +
                "UserName : " + mMobile.getText().toString() +
                " Password : " + mPassword.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerStore() {
        String strPassword = mPassword.getText().toString().trim();
        String selectLocationName = spinner.getSelectedItem().toString();
        String retailAccount = mAccount.getText().toString().trim();
        String storeName = mStoreName.getText().toString().trim();
        String storePoc = mStorePoc.getText().toString().trim();
        String retailPhone = mMobile.getText().toString().trim();
        String retailEmail = mEmail.getText().toString().trim();
        String id = mStoreId.getText().toString().trim();
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "[a-zA-Z0-9._-]+@ust-global.com";

        if (TextUtils.isEmpty(storePoc)) {
            mStorePoc.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(storeName)) {
            mStoreName.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(id)) {
            mStoreId.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(retailAccount)) {
            mAccount.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(retailEmail)) {
            mEmail.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        } else if (!retailEmail.matches(emailPattern)) {
            mEmail.setError(Constants.INVALID_UST_EMAIL_WARNING);
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(retailPhone)) {
            mMobile.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if(spinner.getSelectedItem().toString().equals("Select Location")) {
            Toast.makeText(getActivity(), Constants.SELECT_LOCATION_WARNING, Toast.LENGTH_LONG).show();
            return;
        }

        if (locations != null) {
            for (LocationDetails locationDetails : locations) {
                if (locationDetails.getLocationName().equals(selectLocationName)) {
                    location_id = locationDetails.getLocationId();
                }
            }
        }
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("Loading.....");
            mProgressDialog.show();

        }

        int retailNumber = Integer.parseInt(mStoreId.getText().toString().trim());
        int loginPin = Integer.parseInt(strPassword);
        int wallet = 0;
        RegisterStore registerStore = new RegisterStore();
        registerStore.setLoginUsername(retailPhone);
        registerStore.setLoginPin(loginPin);
        registerStore.setRetailAccount(retailAccount);
        registerStore.setRetailNumber(retailNumber);
        registerStore.setRetailWallet(wallet);
        registerStore.setRetailName(mStoreName.getText().toString());
        registerStore.setLocation_id(location_id);
        registerStore.setRetailEmail(retailEmail);
        registerStore.setRetailPoc(storePoc);


        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.addStoreDetails(getActivity(), registerStore);
        }

    }

    private void updateData() {

        if (mProgressDialog != null) {
            sendMail();
            setSpinner();
            mStorePoc.requestFocus();
            mProgressDialog.dismiss();
            mStoreName.setText("");
            mEmail.setText("");
            mPassword.setText("");
            mAccount.setText("");
            mStoreId.setText("");
            mMobile.setText("");
            mStorePoc.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStorePoc.requestFocus();
        mStoreName.setText("");
        mEmail.setText("");
        mPassword.setText("");
        mAccount.setText("");
        mStoreId.setText("");
        mMobile.setText("");
        mStorePoc.setText("");
    }
}
