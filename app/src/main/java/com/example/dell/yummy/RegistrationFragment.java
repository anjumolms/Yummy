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
import com.example.dell.yummy.webservice.RegistrationResult;
import com.example.dell.yummy.webservice.UserResult;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mUserName = view.findViewById(R.id.register_username);
        mUId = view.findViewById(R.id.register_uid);
        mPassword = view.findViewById(R.id.register_password);
        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mMobile = view.findViewById(R.id.register_mobile);
        mlogin = view.findViewById(R.id.register_login);

        mlogin.setOnClickListener(this);
        return view;
    }


    public void addListener(IMainViewListener iMainViewListener) {

        mainView = iMainViewListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_login:

                String strUserName = mUserName.getText().toString().trim();
                String strEmail = mUId.getText().toString().trim();
                String strPassword = mPassword.getText().toString().trim();
                String strConfirmPassword = mConfirmPassword.getText().toString().trim();
                String strMobile = mMobile.getText().toString().trim();

                if (TextUtils.isEmpty(strUserName)) {
                    mUserName.setError("This field cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(strEmail)) {
                    mUId.setError("This field cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(strPassword)) {
                    mPassword.setError("This field cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(strConfirmPassword)) {
                    mConfirmPassword.setError("This field cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(strMobile)) {
                    mMobile.setError("This field cannot be empty");
                    return;
                }

                if (!strPassword.equals(strConfirmPassword)) {
                    mPassword.setError("Password does not match");
                    return;

                }

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IApiInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RegistrationResult registrationResult = new RegistrationResult();

                registrationResult.setLoginPin(Integer.parseInt(strPassword));
                registrationResult.setUserPhone(strMobile);
                registrationResult.setUserEmail(strEmail);
                registrationResult.setLoginUsername(strMobile);
                registrationResult.setUserName(strUserName);


                IApiInterface service = retrofit.create(IApiInterface.class);
                Call<RegistrationResult> call = service.register(registrationResult);


                call.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call,
                                           Response<RegistrationResult> response) {

                        progressDialog.dismiss();
                        if (response != null) {
                            if (response.code() == 200) {

                                RegistrationResult registrationResult = response.body();

                                if (registrationResult != null) {

                                    if (mainView != null) {
                                        mainView.addFragment(Constants.SCREEN_LOGIN);
                                    }


                                }
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });


                break;

            default:
                break;
        }
    }

}

