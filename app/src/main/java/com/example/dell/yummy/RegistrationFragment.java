package com.example.dell.yummy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


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
                if(mainView != null){
                    mainView.addActivity(Constants.SCREEN_USER_HOME);
                }
                break;


            default:
                break;
        }
    }

}

