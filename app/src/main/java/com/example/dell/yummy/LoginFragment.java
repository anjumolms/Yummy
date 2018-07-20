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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.UserResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUuid;
    private EditText mPassword;
    private Button mLogin;
    private TextView mNewUser;
    private IMainViewListener mMainView;
    private int role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUuid = view.findViewById(R.id.input_uuid);
        mPassword = view.findViewById(R.id.input_password);
        mLogin = view.findViewById(R.id.btn_login);
        mNewUser = view.findViewById(R.id.link_signup);
        mLogin.setOnClickListener(this);
        mNewUser.setOnClickListener(this);
        return view;
    }

    public void addListener(IMainViewListener iMainViewListener) {
        mMainView = iMainViewListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:


                validateUser();

//                if (mMainView != null) {
//                    mMainView.addActivity(Constants.SCREEN_USER_HOME);
//                }}
//                else if(role==2)
//                {
//                    if (mMainView != null) {
//                        mMainView.addActivity(Constants.SCREEN_RETAILER_HOME);
//                    }
//
//                }
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
        String strUserName = mUuid.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(strUserName)) {
            mUuid.setError("This field cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError("This field cannot be empty");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserResult userResult = new UserResult();
        userResult.setLoginUsername(strUserName);
        userResult.setLoginPin(Integer.parseInt(strPassword));

        IApiInterface service = retrofit.create(IApiInterface.class);
        Call<UserResult> call = service.userLogin(userResult);

        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                progressDialog.dismiss();
                if (response != null) {
                    if (response.code() == 200) {

                        UserResult userResult = response.body();
                        if (userResult != null) {
                            if (userResult.getLoginRole() == 1) {
                                //User page
                                if (mMainView != null) {
                                    mMainView.addActivityInfo(Constants.SCREEN_USER_HOME,userResult.getLoginUsername(),
                                            userResult.getUserWallet());
                                }

                            } else if (userResult.getLoginRole() == 2) {

                                //User page NEED CORRECTION
                                if (mMainView != null) {
                                    mMainView.addActivityInfo(Constants.SCREEN_RETAILER_HOME,userResult.getLoginUsername(),
                                            userResult.getUserWallet());
                                }


                                //Retailer page
                            } else if (userResult.getLoginRole() == 3) {
                                // admin page
                            }

                        }

                    } else if (response.code() == 204) {
                        Toast.makeText(getActivity(), "Invalid email or password",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


//                if (role == 1) {
//                    if (mMainView != null) {
//                        mMainView.addActivity(Constants.SCREEN_USER_HOME);
//                    }
//                } else {
//                    if (mMainView != null) {
//                        mMainView.addActivity(Constants.SCREEN_RETAILER_HOME);
//                    }
//
//                }

    }


}
