package com.example.dell.yummy.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.LocationDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IAdminFragmentListener {
    private AdminStoresFragment adminStoresFragment;
    private AdminStoreTransactionsFragment adminStoreTransactionsFragment;
    private RegisterStoresFragment registerStoresFragment;
    private String mUserSelectedLocation;
    private int mLocationId;
    List<LocationDetails> places = null;
    private SharedPreferences sharedPreferences;
    private ProgressDialog mProgressDialog;
    DrawerLayout drawer;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_GET_LOCATION:
                        int flag = intent.getIntExtra("Flag", 0);
                        if (flag == 1) {
                            showPlacesList();
                        }
                        break;
                    case Constants.NOTIFY_GET_ALL_LOCATIONS_LIST:
                        showAddPlacesPopup();
                        break;
                    case Constants.NOTIFY_GET_ALL_LOCATIONS_ERROR:
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setTabColor();
        setupNavigationDrawer();
        initFragments();

        addFragment(Constants.SCREEN_ADMIN_STORE_LIST);
    }

    private void initFragments() {
        adminStoresFragment = new AdminStoresFragment();
        adminStoresFragment.addListener(this);

        adminStoreTransactionsFragment = new AdminStoreTransactionsFragment();
        adminStoreTransactionsFragment.addListener(this);

        registerStoresFragment = new RegisterStoresFragment();
        registerStoresFragment.addListener(this);
        mProgressDialog = new ProgressDialog(getApplicationContext());

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_GET_LOCATION);
        intentFilter.addAction(Constants.NOTIFY_GET_ALL_LOCATIONS_LIST);
        intentFilter.addAction(Constants.NOTIFY_GET_ALL_LOCATIONS_ERROR);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, intentFilter);
        sharedPreferences
                = this.getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);

        checkLocation();
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getLocation(getApplicationContext(), 0);
        }

    }

    private void loadDetails() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            int id = getLocationIdFromSharedPreferance();
            calls.getStoreDetails(getApplicationContext(), id);
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


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
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
            finish();
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
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        RetrofitNetworksCalls calls
                = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            places = calls.getLocations();
        }
        if (places != null) {

            Dialog dialog;
            List<String> items = new ArrayList<>();
            for (LocationDetails locationDetails : places) {
                items.add(locationDetails.getLocationName());
            }
            mLocationId = places.get(0).getLocationId();
            final String[] placesList = items.toArray(new String[items.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your location: ");
            builder.setSingleChoiceItems(placesList, 0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mUserSelectedLocation = placesList[which];
                            for (LocationDetails locationDetails : places) {
                                if (locationDetails.getLocationName().equals(mUserSelectedLocation)) {
                                    mLocationId = locationDetails.getLocationId();
                                    break;
                                }
                            }
                        }
                    });

            builder.setPositiveButton("Done!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked
                            addLocationTosharedPreferance();
                            loadDetails();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    addLocationTosharedPreferance();
                                    loadDetails();
                                    dialog.dismiss();
                                }
                            });
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private void addLocationTosharedPreferance() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constants.KEY_LOCATION, mLocationId);
            editor.commit();
        }
    }

    private void checkLocation() {

        if (sharedPreferences != null) {
            int atnIn = sharedPreferences.getInt(Constants.KEY_LOCATION, 0);
            if (atnIn == 0) {
                RetrofitNetworksCalls calls = DataSingleton
                        .getInstance().getRetrofitNetworksCallsObject();
                if (calls != null) {
                    calls.getLocation(getApplicationContext(), 1);
                    // mProgressDialog.show();
                }
            } else {
                loadDetails();
            }
        }

    }

    public int getLocationIdFromSharedPreferance() {
        int location = 0;
        if (sharedPreferences != null) {
            location = sharedPreferences.getInt(Constants.KEY_LOCATION, 0);
        }
        return location;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
            //showAddPlacesPopup();
            RetrofitNetworksCalls calls = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                List<LocationDetails> locationDetails = calls.getmGetAllLocationDetails();
                if (locationDetails != null) {
                    showAddPlacesPopup(locationDetails);
                } else {
                    //mProgressDialog.setMessage("Loading........");
                    // mProgressDialog.show();
                    calls.getAllLocations(getApplicationContext());
                }
            }
        } else if (id == R.id.admin_nav_logout) {
            finishAffinity();
        } else if (id == R.id.nav_admin_location) {
            showPlacesList();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAddPlacesPopup() {
        mProgressDialog.dismiss();
        RetrofitNetworksCalls calls = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            List<LocationDetails> locationDetails = calls.getmGetAllLocationDetails();
            if (locationDetails != null) {
                showAddPlacesPopup(locationDetails);
            }
        }
    }

    private void showAddPlacesPopup(final List<LocationDetails> locationDetails) {

        // ...Irrelevant code for customizing the buttons and title
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_places);

        final Spinner spinner = dialog.findViewById(R.id.sp_admin_spinner);
        TextView btAdd = dialog.findViewById(R.id.popup_admin_add_place);
        TextView btCancel = dialog.findViewById(R.id.popup_admin_places_cancel);

        ArrayList<String> placesList = new ArrayList<>();
        placesList.add("Select Location");

        for (LocationDetails details : locationDetails) {
            placesList.add(details.getLocationName());
        }

        ArrayAdapter<String> dataAdapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, placesList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = spinner.getSelectedItem().toString();
                LocationDetails selectedLocation = null;
                if (!name.equalsIgnoreCase("Select Location")) {
                    for (LocationDetails details : locationDetails) {
                        if (details.getLocationName().equalsIgnoreCase(name)) {
                            selectedLocation = details;
                            break;
                        }
                    }

                    RetrofitNetworksCalls calls = DataSingleton
                            .getInstance().getRetrofitNetworksCallsObject();
                    if (calls != null) {
                        calls.addPlaces(selectedLocation, getApplicationContext());
                    }
                    dialog.dismiss();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


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
        adminStoreTransactionsFragment.setStores(storeDetails);

//        if (storeDetails != null) {
//            RetrofitNetworksCalls retrofitNetworksCalls
//                    = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
//            if (retrofitNetworksCalls != null) {
//
//                retrofitNetworksCalls.getAllTransactionDetails(getApplicationContext(),
//                        storeDetails.getRetailId());
//            }
//
//        }
    }

    @Override
    public void showNavigationDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }
}
