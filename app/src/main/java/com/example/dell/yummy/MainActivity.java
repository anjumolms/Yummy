package com.example.dell.yummy;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.dell.yummy.Retailer.RetailerHomeActivity;
import com.example.dell.yummy.admin.AdminHomeActivity;
import com.example.dell.yummy.dbhandler.DbHandler;
import com.example.dell.yummy.user.UserHomeActivity;
import com.example.dell.yummy.model.StoreDetails;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.dell.yummy.Constants.SCREEN_LOGIN;

public class MainActivity extends AppCompatActivity implements IMainViewListener {
    private LoginFragment mLoginFragment;
    private RegistrationFragment mRegistrationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTabColor();
        initFragments();
        addFragment(SCREEN_LOGIN);
    }

    private void initFragments() {
        mLoginFragment = new LoginFragment();
        mLoginFragment.addListener(this);
        mRegistrationFragment = new RegistrationFragment();
        mRegistrationFragment.addListener(this);
    }

    private void setTabColor() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_color));
        }
    }

    @Override
    public void addFragment(int screenId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {
            case SCREEN_LOGIN:
                fragmentTransaction.replace(R.id.fl_main_fragment_container,
                        mLoginFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_REGISTRATION:
                fragmentTransaction.replace(R.id.fl_main_fragment_container,
                        mRegistrationFragment);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public void addActivityInfo(int screen) {

        switch (screen) {
            case Constants.SCREEN_USER_HOME:
                Intent mySuperIntent = new Intent(this,
                        UserHomeActivity.class);
                startActivity(mySuperIntent);
                break;
            case Constants.SCREEN_RETAILER_HOME:
                Intent intent = new Intent(this, RetailerHomeActivity.class);
                startActivity(intent);
                break;
            case Constants.SCREEN_ADMIN_HOME:
                Intent adminIntent = new Intent(this, AdminHomeActivity.class);
                startActivity(adminIntent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mRegistrationFragment.isVisible()) {
            addFragment(SCREEN_LOGIN);
        } else {
            onDestroy();
        }
    }
}

