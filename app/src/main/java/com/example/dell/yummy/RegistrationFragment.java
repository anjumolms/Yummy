package com.example.dell.yummy;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.RegistrationResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private IMainViewListener mainView;
    private EditText mUserName;
    private EditText mUId;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mMobile;
    private Button mlogin;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews(view);
        mlogin.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mUserName = view.findViewById(R.id.register_username);
        mUId = view.findViewById(R.id.register_uid);
        mPassword = view.findViewById(R.id.register_password);
        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mMobile = view.findViewById(R.id.register_mobile);
        mlogin = view.findViewById(R.id.register_login);
    }


    public void addListener(IMainViewListener iMainViewListener) {

        mainView = iMainViewListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_login:
                getValuesFromFields();
                break;

            default:
                break;
        }
    }

    private void getValuesFromFields() {

        String strUserName = mUserName.getText().toString().trim();
        String strEmail = mUId.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();
        String strConfirmPassword = mConfirmPassword.getText().toString().trim();
        String strMobile = mMobile.getText().toString().trim();


        if (TextUtils.isEmpty(strUserName)) {
            mUserName.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(strEmail)) {
            mUId.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(strConfirmPassword)) {
            mConfirmPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (TextUtils.isEmpty(strMobile)) {
            mMobile.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (!strPassword.equals(strConfirmPassword)) {
            mPassword.setError(Constants.FIELD_PASSWORD_INCORRECT);
            return;

        }

        RegistrationResult registrationResult = new RegistrationResult();
        registrationResult.setLoginPin(Integer.parseInt(strPassword));
        registrationResult.setUserPhone(strMobile);
        registrationResult.setUserEmail(strEmail);
        registrationResult.setLoginUsername(strMobile);
        registrationResult.setUserName(strUserName);
        showProgress();
        registerUser(registrationResult);
    }

    private void registerUser(RegistrationResult registrationResult) {
        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<RegistrationResult> call = service.register(registrationResult);
            call.enqueue(new Callback<RegistrationResult>() {
                @Override
                public void onResponse(Call<RegistrationResult> call,
                                       Response<RegistrationResult> response) {
                    mProgressDialog.dismiss();
                    if (response != null && response.code() == 200) {

                        RegistrationResult registrationResult = response.body();
                        if (registrationResult != null && mainView != null) {
                            mainView.addFragment(Constants.SCREEN_LOGIN);
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResult> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(),
                            t.getMessage(), Toast.LENGTH_LONG).show();

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

