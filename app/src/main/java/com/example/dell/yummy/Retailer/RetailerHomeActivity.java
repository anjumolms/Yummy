package com.example.dell.yummy.Retailer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import de.hdodenhof.circleimageview.CircleImageView;

public class RetailerHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IRetailerFragmentListener {
    private RetailerTransactionDetailsFragment mretailerTransactionDetailsFragment;
    private EachTransactionFragment meachTransactionFragment;
    private RetailerAddItemFragment mretailerAddItemFragment;
    private RetailerListItemFragment mretailerListItemFragment;
    private RetailerWalletFragment mretailerWalletFragment;
    private ConfirmOrdersFragment mConfirmOrdersFragment;
    private int counter;
    private CoordinatorLayout mCoordinatorLayout;
    private RefundFragment mRefundFragment;
    private SharedPreferences sharedPreferences;
    private ProfileFragment mProfileFragment;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);
        sharedPreferences
                = this.getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);
        setTabColor();
        setupNavigationDrawer();
        initFragments();
        addFragment(Constants.SCREEN_RETAILER_TRANSACTION_DETAILS);

    }

    private void setTabColor() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
        }
    }

    private void initFragments() {

        mCoordinatorLayout = findViewById(R.id.cl_retailer_activity);
        mretailerTransactionDetailsFragment = new RetailerTransactionDetailsFragment();
        meachTransactionFragment = new EachTransactionFragment();
        meachTransactionFragment.addListener(this);
        mretailerTransactionDetailsFragment.addListener(this);
        mretailerAddItemFragment = new RetailerAddItemFragment();
        mretailerAddItemFragment.addListener(this);
        mretailerListItemFragment = new RetailerListItemFragment();
        mretailerListItemFragment.addListener(this);
        mretailerWalletFragment = new RetailerWalletFragment();
        mretailerWalletFragment.addListener(this);
        mConfirmOrdersFragment = new ConfirmOrdersFragment();
        mConfirmOrdersFragment.addListener(this);
        mProfileFragment = new ProfileFragment();
        mProfileFragment.addListener(this);

        mRefundFragment = new RefundFragment();
        mRefundFragment.addListener(this);

        loadDetails();
    }

    public void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, "Sorry you are offline",
                        Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showNavigationDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }

    private void loadDetails() {
        if (isNetworkAvailable()) {

            RetrofitNetworksCalls retrofitNetworksCalls
                    = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {

                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                                Context.MODE_PRIVATE);
                int id = 0;
                if (sharedPreferences != null) {
                    id = sharedPreferences.getInt(Constants.KEY_ID, 0);
                }

                retrofitNetworksCalls.getTransactionPendingOrders(getApplicationContext(), id);
                retrofitNetworksCalls.getRetailerMenuList(getApplicationContext(), 0);
            }
        } else {
            showSnackBar();
        }
    }

    @Override
    public void addFragment(int screenId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {

            case Constants.SCREEN_RETAILER_TRANSACTION_DETAILS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mretailerTransactionDetailsFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_EACH_TRANSACTION_DETAILS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        meachTransactionFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_ADD_ITEMS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mretailerAddItemFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_LIST_ITEMS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mretailerListItemFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_WALLET:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mretailerWalletFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_CONFIRM_ORDERS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mConfirmOrdersFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_REFUND_FRAGMENT:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mRefundFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_PROFILE_EDIT:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
                        mProfileFragment);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void loadEachTransactionFragment(Order details) {
        if (meachTransactionFragment != null) {
            meachTransactionFragment.listPosition(details);
            addFragment(Constants.SCREEN_RETAILER_EACH_TRANSACTION_DETAILS);
        }
    }

    @Override
    public void showItemUpdatePopup(final DishesDetails dishesDetails) {
        if (mretailerListItemFragment != null) {
            mretailerListItemFragment.showUpdatePopup(dishesDetails);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager ConnectionManager
                = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            return true;
        } else {
            return false;
        }

    }

    private void setupNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.retailer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View hView = navigationView.getHeaderView(0);

        TextView mProfileName = hView.findViewById(R.id.tv_retailer_profilename);
        CircleImageView circleImageView = hView.findViewById(R.id.profile_image_retailer);
        if (sharedPreferences != null) {
            String name = sharedPreferences.getString(Constants.KEY_RETAIL_NAME, "");
            mProfileName.setText(name);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileFragment();
            }
        });

        navigationView.setItemIconTintList(null);
    }

    public void showProfileFragment(){
        addFragment(Constants.SCREEN_PROFILE_EDIT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mretailerWalletFragment.isVisible()
                || mretailerListItemFragment.isVisible()
                || mretailerAddItemFragment.isVisible()
                || meachTransactionFragment.isVisible()
                || mConfirmOrdersFragment.isVisible()
                || mRefundFragment.isVisible()
                || mProfileFragment.isVisible()) {

//            RetailerTransactionDetailsFragment fragment = new RetailerTransactionDetailsFragment();
//            fragment.addListener(this);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container,
//                    fragment);
//            fragmentTransaction.commit();
            addFragment(Constants.SCREEN_RETAILER_TRANSACTION_DETAILS);


        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.retailer_home, menu);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_item) {

            addFragment(Constants.SCREEN_RETAILER_ADD_ITEMS);

            // Handle the camera action
        } else if (id == R.id.list_item) {

            addFragment(Constants.SCREEN_RETAILER_LIST_ITEMS);

        } else if (id == R.id.wallet) {

            addFragment(Constants.SCREEN_WALLET);

        } else if (id == R.id.retailer_logout) {
            finishAffinity();
        } else if (id == R.id.confirm_orders) {
            addFragment(Constants.SCREEN_CONFIRM_ORDERS);
        } else if (id == R.id.refund_orders) {
            addFragment(Constants.SCREEN_REFUND_FRAGMENT);
        } else if(id == R.id.retailer_profile){
            showProfileFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
