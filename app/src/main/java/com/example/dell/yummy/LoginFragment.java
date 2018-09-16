package com.example.dell.yummy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.UserResult;

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
    DbHandler dbHandler ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        mLogin.setOnClickListener(this);
        mNewUser.setOnClickListener(this);
        dbHandler =new DbHandler(getActivity());
        return view;
    }

    private void initViews(View view) {
        mUuid = view.findViewById(R.id.input_uuid);
        mPassword = view.findViewById(R.id.input_password);
        mLogin = view.findViewById(R.id.btn_login);
        mNewUser = view.findViewById(R.id.link_signup);

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

        dbHandler.getToken(1);

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

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                    progressDialog.dismiss();
                    checkUser(response);
//                    UserResult userResult1 = new UserResult();
//                    userResult1.setLoginUsername("Athira");
//                    userResult1.setUserWallet(20);
//                    userResult1.setLoginPin(123);
//                    showUserPage(userResult1);

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

    }

    private void checkUser(Response<UserResult> response) {
        if (response != null && response.code() == 200) {
            UserResult userResult = response.body();
            if (userResult != null) {
                switch (userResult.getLoginRole()) {
                    case 1:
                        showUserPage(userResult);
                        break;
                    case 2:
                        showRetailerPage(userResult);
                        break;
                    case 3:
                        showadminPage(userResult);
                        break;

                }
            } else {
                //TODO: Notify user result is null.
            }
        } else {
            Toast.makeText(getActivity(), response.code()
                    + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showadminPage(UserResult userResult) {
    }

    private void showRetailerPage(UserResult userResult) {
        //User page NEED CORRECTION
        if (mMainView != null) {
            SharedPreferences sharedpreferences
                    = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.KEY_USER_NAME, userResult.getLoginUsername());
            editor.putInt(Constants.KEY_LOGIN_PIN, userResult.getLoginPin());
            editor.putInt(Constants.KEY_WALLET, userResult.getUserWallet());
            editor.commit();
            mMainView.addActivityInfo(Constants.SCREEN_RETAILER_HOME,
                    userResult.getLoginUsername(),
                    userResult.getUserWallet(),
                    userResult.getUserId());
        }

    }

    private void showUserPage(UserResult userResult) {
        //User page
        if (mMainView != null) {
            SharedPreferences sharedpreferences
                    = getActivity().getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.KEY_USER_NAME, userResult.getLoginUsername());
            editor.putInt(Constants.KEY_LOGIN_PIN, userResult.getLoginPin());
            editor.putInt(Constants.KEY_WALLET, userResult.getUserWallet());
            editor.commit();

            mMainView.addActivityInfo(Constants.SCREEN_USER_HOME,
                    userResult.getLoginUsername(),
                    userResult.getUserWallet(),
                    userResult.getUserId());
        }
    }
}
