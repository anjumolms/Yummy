package com.example.dell.yummy;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.dell.yummy.Retailer.RetailerHomeActivity;
import com.example.dell.yummy.user.UserHomeActivity;
import com.example.dell.yummy.webservice.StoreDetails;

import java.io.Serializable;
import java.util.List;

import static com.example.dell.yummy.Constants.SCREEN_LOGIN;

public class MainActivity extends AppCompatActivity implements IMainViewListener {
    private LoginFragment mLoginFragment;
    private RegistrationFragment mRegistrationFragment;
    List<StoreDetails> storeDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
        }

        Intent intent = getIntent();
        if(intent != null){
            storeDetailsList = (List<StoreDetails>) intent.getSerializableExtra("KeyStoreList");
        }

        mLoginFragment = new LoginFragment();
        mLoginFragment.addListener(this);

        mRegistrationFragment = new RegistrationFragment();
        mRegistrationFragment.addListener(this);


        addFragment(SCREEN_LOGIN);
    }

    @Override
    public void addFragment(int screenId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {
            case SCREEN_LOGIN:
                fragmentTransaction.replace(R.id.fl_main_fragment_container, mLoginFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_REGISTRATION:
                fragmentTransaction.replace(R.id.fl_main_fragment_container, mRegistrationFragment);
                fragmentTransaction.commit();
                break;
                }
    }



    @Override
    public void addActivityInfo(int screen, String name, int wallet,int userID) {

        switch (screen) {
            case Constants.SCREEN_USER_HOME:

                Intent mySuperIntent = new Intent(this,
                        UserHomeActivity.class);
                mySuperIntent.putExtra("Key1", name);
                mySuperIntent.putExtra("Key2", wallet);
                mySuperIntent.putExtra("Key3",userID);
                if(storeDetailsList != null){
                    mySuperIntent.putExtra("KeyStoreList", (Serializable) storeDetailsList);
                    }
                startActivity(mySuperIntent);
                break;


                case Constants.SCREEN_RETAILER_HOME:
                    Intent intent = new Intent(this, RetailerHomeActivity.class);
                    startActivity(intent);
                    break;


            default: break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mRegistrationFragment.isVisible()){
            addFragment(SCREEN_LOGIN);
        }
    }
}

