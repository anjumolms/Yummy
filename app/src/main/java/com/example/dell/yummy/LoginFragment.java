package com.example.dell.yummy;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.dbhandler.DbHandler;
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.model.UserDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.UserResult;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText mUuid;
    private EditText mPassword;
    private Button mLogin;
    private TextView mNewUser;
    private IMainViewListener mMainView;
    DbHandler dbHandler;
    private CoordinatorLayout mCoordinatorLayout;
    ProgressDialog progressDialog;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_USER_DETAILS:
                        showUserPage();
                        break;
                    case Constants.NOTIFY_RETAILER_DETAILS:
                        showRetailerPage();
                        break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container,
                false);
        initViews(view);
        mLogin.setOnClickListener(this);
        mNewUser.setOnClickListener(this);
        dbHandler = new DbHandler(getActivity());
        return view;
    }

    private void initViews(View view) {
        mUuid = view.findViewById(R.id.input_uuid);
        mPassword = view.findViewById(R.id.input_password);
        mLogin = view.findViewById(R.id.btn_login);
        mNewUser = view.findViewById(R.id.link_signup);
        mCoordinatorLayout = view.findViewById(R.id.cl_login_fragment);
        progressDialog = new ProgressDialog(getActivity());
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_USER_DETAILS);
        intentFilter.addAction(Constants.NOTIFY_RETAILER_DETAILS);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

    }

    public void addListener(IMainViewListener iMainViewListener) {

        mMainView = iMainViewListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:
                validateUser();
                break;

            case R.id.link_signup:
                if (mMainView != null) {
                    mMainView.addFragment(Constants.SCREEN_REGISTRATION);
                }
                break;
            default:
                break;
        }
    }

    private void validateUser() {

        //dbHandler.getToken(1);

        String strUserName = mUuid.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(strUserName)) {
            mUuid.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (isNetworkAvailable()) {

            progressDialog.setMessage("Signing Up...");
            progressDialog.show();

            final UserResult userResult = new UserResult();
            userResult.setLoginUsername(strUserName);
            userResult.setLoginPin(Integer.parseInt(strPassword));


            Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
            if (retrofit != null) {
                IApiInterface service = retrofit.create(IApiInterface.class);
                Call<UserResult> call = service.userLogin(userResult);

                call.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call,
                                           Response<UserResult> response) {
                        checkUser(response);
//                    UserResult userResult1 = new UserResult();
//                    userResult1.setLoginUsername("Athira");
//                    userResult1.setUserWallet(20);
//                    userResult1.setLoginPin(123);
//                    mMainView.addActivityInfo(Constants.SCREEN_USER_HOME);
//                    mMainView.addActivityInfo(Constants.SCREEN_RETAILER_HOME);

                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Log.w("LoginFragment ", "Retrofit object is null ");
            }
        } else {

            Snackbar snackbar = Snackbar
                    .make(mCoordinatorLayout, "Sorry you are offline",
                            Snackbar.LENGTH_LONG);
            snackbar.show();

        }

    }

    private void checkUser(Response<UserResult> response) {
        if (response != null && response.code() == 200) {
            RetrofitNetworksCalls calls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (calls != null) {
                UserResult userResult = response.body();
                if (userResult != null) {
                    switch (userResult.getLoginRole()) {
                        case 1:
                            calls.getUserDetails(getActivity(), userResult.getLoginId());
                            updateToken(userResult, calls);

                            break;
                        case 2:
                            calls.getRetailerDetails(getActivity(), userResult.getLoginId());
                            updateToken(userResult, calls);
                            break;
                        case 3:
                            showadminPage();
                            break;

                    }
                } else {
                    //TODO: Notify user result is null.
                }
            }

        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(getActivity(), response.code()
                    + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToken(UserResult userResult, RetrofitNetworksCalls calls) {
        if (userResult.getLoginRole() == 1
                || userResult.getLoginRole() == 2) {

            if (userResult.getToken().isEmpty()) {
                if (dbHandler != null) {
                    String token = dbHandler.getToken(1);
                    userResult.setToken(token);
                    calls.updateToken(getActivity(), userResult);
                }
            } else {
                String token = userResult.getToken();
            }
        }

    }

    private void showadminPage() {
        if (mMainView != null) {
            //addDetailsToSharedPreferance(userResult);
            mMainView.addActivityInfo(Constants.SCREEN_ADMIN_HOME);
        }
    }

    private void addUserDetailsToSharedPreferance(UserDetails userResult) {
        if (userResult != null) {

            SharedPreferences sharedpreferences
                    = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.KEY_USER_NAME, userResult.getUserName());
            editor.putInt(Constants.KEY_LOGIN_PIN, userResult.getLoginPin());
            editor.putInt(Constants.KEY_WALLET, userResult.getUserWallet());
            editor.putInt(Constants.KEY_ID, userResult.getUserId());
            editor.putInt(Constants.KEY_ROLE, 1);
            editor.commit();
        }
    }

    private void showRetailerPage() {
        //User page NEED CORRECTION
        if (mMainView != null) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null && calls.getmRetailerDetails() != null) {

                addRetailerDetailsToSharedPreferance(calls.getmRetailerDetails());
                mMainView.addActivityInfo(Constants.SCREEN_RETAILER_HOME);
            }
        }

    }

    private void addRetailerDetailsToSharedPreferance(RetailerDetails retailerDetails) {
        if (retailerDetails != null) {

            SharedPreferences sharedpreferences
                    = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.KEY_USER_NAME, retailerDetails.getLoginUsername());
            editor.putInt(Constants.KEY_LOGIN_PIN, retailerDetails.getLoginPin());
            editor.putInt(Constants.KEY_WALLET, retailerDetails.getRetailWallet());
            editor.putInt(Constants.KEY_ID, retailerDetails.getRetailId());
            editor.putInt(Constants.KEY_ROLE, 2);
            editor.commit();
        }
    }

    private void showUserPage() {
        //User page
        if (mMainView != null) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null && calls.getUsersDetails() != null) {
                addUserDetailsToSharedPreferance(calls.getUsersDetails());
                mMainView.addActivityInfo(Constants.SCREEN_USER_HOME);
            }
        }
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
