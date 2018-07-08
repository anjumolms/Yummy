package com.example.dell.yummy;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUuid;
    private EditText mPassword;
    private Button mLogin;
    private TextView mNewUser;
    private IMainViewListener mMainView;

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

        switch (v.getId()){
            case R.id.btn_login:
                if(mMainView != null){
                    mMainView.addActivity(Constants.SCREEN_USER_HOME);
                }
                
                break;

            case R.id.link_signup:

                if(mMainView != null){
                    mMainView.addFragment(Constants.SCREEN_REGISTRATION);
                }

                break;
            default:
                break;
        }
    }
 }
