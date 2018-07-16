package com.example.dell.yummy;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.yummy.Retailer.RetailerHomeActivity;
import com.example.dell.yummy.user.UserHomeActivity;

public class MainActivity extends AppCompatActivity implements IMainViewListener {
    private LoginFragment mLoginFragment;
    private RegistrationFragment mRegistrationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginFragment = new LoginFragment();
        mLoginFragment.addListener(this);

        mRegistrationFragment = new RegistrationFragment();
        mRegistrationFragment.addListener(this);


        addFragment(Constants.SCREEN_LOGIN);
    }

    @Override
    public void addFragment(int screenId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {
            case Constants.SCREEN_LOGIN:
                fragmentTransaction.replace(R.id.fl_main_fragment_container, mLoginFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_REGISTRATION:
                fragmentTransaction.replace(R.id.fl_main_fragment_container, mRegistrationFragment);
                fragmentTransaction.commit();
                break;
                }
    }

    public void addActivity(int screen){

        switch (screen){
            case Constants.SCREEN_RETAILER_HOME:
            Intent intent = new Intent(this, RetailerHomeActivity.class);

            startActivity(intent);
            break;
            default: break;

        }
      //
    }

    @Override
    public void addActivityInfo(int screen, String name, int wallet) {

        switch (screen) {
            case Constants.SCREEN_USER_HOME:

                Intent mySuperIntent = new Intent(this,UserHomeActivity.class);
                mySuperIntent.putExtra("Key1", name);
                mySuperIntent.putExtra("Key2", wallet);
                startActivity(mySuperIntent);
                break;
//            case Constants.SCREEN_RETAILER_HOME:
//                Intent intent = new Intent(this, RetailerHomeActivity.class);
//
//                startActivity(intent);
//                break;
            default: break;
        }

    }

}

