package com.example.dell.yummy.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IAdminFragmentListener {
    private AdminStoresFragment adminStoresFragment;
    private AdminStoreTransactionsFragment adminStoreTransactionsFragment;
    private RegisterStoresFragment registerStoresFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setTabColor();
        setupNavigationDrawer();
        initFragments();
        loadDetails();
        showPlacesList();
        addFragment(Constants.SCREEN_ADMIN_STORE_LIST);
    }

    private void initFragments() {
        adminStoresFragment = new AdminStoresFragment();
        adminStoresFragment.addListener(this);

        adminStoreTransactionsFragment = new AdminStoreTransactionsFragment();
        adminStoreTransactionsFragment.addListener(this);

        registerStoresFragment = new RegisterStoresFragment();

    }

    private void loadDetails() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getStoreDetails(getApplicationContext());
        }
    }

    private void setTabColor() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
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
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (adminStoreTransactionsFragment.isVisible()
                || registerStoresFragment.isVisible()) {
            addFragment(Constants.SCREEN_ADMIN_STORE_LIST);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
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

    private void showPlacesList() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Choose your location");
        String[] types = {"Campus ", "Bhavani"};
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        //onZipRequested();
                        break;
                    case 1:
                        //onCategoryRequested();
                        break;
                }
            }

        });

        b.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_admin_coin) {

        } else if (id == R.id.nav_admin_add_retailer) {
            addFragment(Constants.SCREEN_REGISTER_RETAILER);
        } else if (id == R.id.nav_admin_wallet) {

        } else if (id == R.id.nav_admin_places) {
            showAddPlacesPopup();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAddPlacesPopup() {

    }

    @Override
    public void addFragment(int screenId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {

            case Constants.SCREEN_ADMIN_STORE_LIST:
                fragmentTransaction.replace(R.id.fl_admin_home_fragment_container,
                        adminStoresFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_ADMIN_TRANSACTION_DETAILS:
                fragmentTransaction.replace(R.id.fl_admin_home_fragment_container,
                        adminStoreTransactionsFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_REGISTER_RETAILER:
                fragmentTransaction.replace(R.id.fl_admin_home_fragment_container,
                        registerStoresFragment);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }

    @Override
    public void passStoreDetails(int screenRetailerTransactionDetails,
                                 StoreDetails storeDetails) {
        addFragment(Constants.SCREEN_ADMIN_TRANSACTION_DETAILS);
        if (storeDetails != null) {
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

                retrofitNetworksCalls.getTransactionDetails(getApplicationContext(), id);
            }

        }
    }
}
