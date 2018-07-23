package com.example.dell.yummy.Retailer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.user.store.StoreDetailsFragment;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.List;

public class RetailerHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentListener {

    private RetailerTransactionDetailsFragment mretailerTransactionDetailsFragment;
    private EachTransactionFragment meachTransactionFragment;
    private RetailerAddItemFragment mretailerAddItemFragment;
    private RetailerListItemFragment mretailerListItemFragment;
    private RetailerWalletFragment mretailerWalletFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
        }
        setupNavigationDrawer();

        mretailerTransactionDetailsFragment = new RetailerTransactionDetailsFragment();
        meachTransactionFragment = new EachTransactionFragment();
        mretailerTransactionDetailsFragment.addListener(this);
        mretailerAddItemFragment = new RetailerAddItemFragment();
        mretailerAddItemFragment.addListener(this);
        mretailerListItemFragment = new RetailerListItemFragment();
        mretailerListItemFragment.addListener(this);
        mretailerWalletFragment = new RetailerWalletFragment();

        addFragment(Constants.SCREEN_RETAILER_TRANSACTION_DETAILS);


    }

    @Override
    public void addFragment(int screenId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {

            case Constants.SCREEN_RETAILER_TRANSACTION_DETAILS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container, mretailerTransactionDetailsFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_EACH_TRANSACTION_DETAILS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container, meachTransactionFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_ADD_ITEMS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container, mretailerAddItemFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_RETAILER_LIST_ITEMS:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container, mretailerListItemFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_WALLET:
                fragmentTransaction.replace(R.id.fl_retailer_home_fragment_container, mretailerWalletFragment);
                fragmentTransaction.commit();
                break;

            default:
                break;


        }
    }

    @Override
    public void passStoreDetails(int screenId, StoreDetails storeDetails) {

    }

    @Override
    public void addPopup(DishesDetails dishesDetails) {

    }

    @Override
    public void loadConformationFragment(List<DishesDetails> dishesDetails) {

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


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
