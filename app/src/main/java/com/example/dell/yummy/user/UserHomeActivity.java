package com.example.dell.yummy.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.store.StoreDetailsFragment;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentListener {

    private UserViewPagerFragment mUserViewPagerFragment;
    private StoreDetailsFragment mStoreDetailsFragment;
    private PaymentDetailsFragment mPaymentDetailsFragment;
    private UserAddCoinsFragment mUserAddCoinsFragment;
    private UserWalletFragment mUserWalletFragment;
    private TextView mProfileName;
    private int coins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        setupNavigationDrawer();


        mUserViewPagerFragment = new UserViewPagerFragment();
        mUserViewPagerFragment.addListener(this);

        mStoreDetailsFragment = new StoreDetailsFragment();
        mStoreDetailsFragment.addListener(this);

        mPaymentDetailsFragment = new PaymentDetailsFragment();
        mPaymentDetailsFragment.addListener(this);

        mUserAddCoinsFragment = new UserAddCoinsFragment();
        mUserAddCoinsFragment.addListener(this);

        mUserWalletFragment = new UserWalletFragment();
        mUserWalletFragment.addListener(this);

        addFragment(Constants.SCREEN_USER);

    }

    @Override
    public void addFragment(int screenId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {

            case Constants.SCREEN_USER:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container, mUserViewPagerFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_STORE_DETAILS:

                fragmentTransaction.replace(R.id.fl_userhome_fragment_container, mStoreDetailsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_DISHES_DETAILS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container, mPaymentDetailsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_ADD_COINS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,mUserAddCoinsFragment );
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_WALLET:
                Bundle bundle = new Bundle();
                bundle.putInt("Key2", coins);
                mUserWalletFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,mUserWalletFragment );
                fragmentTransaction.commit();
                break;

            default:
                break;


        }
    }

    private void setupNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        mProfileName = hView.findViewById(R.id.tv_profilename);
        Intent myIntent = getIntent();

        if(myIntent != null) {

            String fName = myIntent.getStringExtra("Key1");
            coins  = myIntent.getIntExtra("Key2", 0);

            if(fName != null && !fName.isEmpty()) {

                mProfileName.setText(fName);
            }
        }
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_coin) {
            // Handle the coin action
            addFragment(Constants.SCREEN_USER_ADD_COINS);

        } else if (id == R.id.nav_wallet) {
            // Handle the wallet action
            addFragment(Constants.SCREEN_USER_WALLET);

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
