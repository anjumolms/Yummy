package com.example.dell.yummy.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.LocationDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.UserReview;
import com.example.dell.yummy.user.store.OrderSuccessfullFragment;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.user.store.ConfirmationFragment;
import com.example.dell.yummy.user.store.StoreDetailsFragment;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IUserFragmentListener, RatingDialogListener, DrawerLayout.DrawerListener {

    private UserViewPagerFragment mUserViewPagerFragment;
    private StoreDetailsFragment mStoreDetailsFragment;
    private PurchaseHistoryFragment mPurchaseHistoryFragment;
    private UserAddCoinsFragment mUserAddCoinsFragment;
    private UserWalletFragment mUserWalletFragment;
    private ConfirmationFragment mConfirmationFragment;
    private OrderSuccessfullFragment mOrderSuccessfullFragment;
    private PurchaseHistoryItemFragment mPurchaseHistoryItemFragment;
    private FrameLayout mFrameLayout;
    private TextView mProfileName;
    private UserReview mUserReview;
    private CoordinatorLayout mCoordinatorLayout;
    int reviewId;
    List<LocationDetails> places = null;
    DrawerLayout drawer;
    int counter = 0;
    int flag =0;
    DishesDetails dishFromApi;
    private ProgressDialog mProgressDialog;
    private Menu mMenuList;
    private MenuItem mMenuItemWallet;
    private String mUserSelectedLocation;
    private int mLocationId;
    private SharedPreferences sharedPreferences;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_USER_CONFIRM_ORDER:
                        getUserWalletDetails();
                        break;
                    case Constants.NOTIFY_WALLET_UPDATED:
                        dismissProgress();
                        break;
                    case Constants.NOTIFY_REVIEW_UPDATED:
                        removeItemsFromReviewList();
                        break;
                    case Constants.NOTIFY_GET_LOCATION:
                        int flag = intent.getIntExtra("Flag", 0);
                        if (flag == 1 && !isFinishing()) {
                            showPlacesList();
                        }
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
        setContentView(R.layout.activity_user_home);
        mFrameLayout = findViewById(R.id.fl_userhome_fragment_container);
        mCoordinatorLayout = findViewById(R.id.cl_user_home_activity);
        sharedPreferences
                = this.getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);
        setTabColor();
        setupNavigationDrawer();
        initFragments();
        addFragment(Constants.SCREEN_USER);
        //LoadDetails();
        //showPlacesList();

    }

    private void LoadDetails() {
        if (isNetworkAvailable()) {

            RetrofitNetworksCalls retrofitNetworksCalls
                    = DataSingleton.getInstance().getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                int location = getLocationIdFromSharedPreferance();
                retrofitNetworksCalls.getStoreDetails(getApplicationContext(), location);
                retrofitNetworksCalls.getDishDetails(getApplicationContext(), location);
                retrofitNetworksCalls.getReviewDetails(getApplicationContext());

            }
        } else {
            showSnackBar();
        }
    }

    public void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, "Sorry you are offline",
                        Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void initFragments() {
        mUserViewPagerFragment = new UserViewPagerFragment();
        mUserViewPagerFragment.addListener(this);

        mStoreDetailsFragment = new StoreDetailsFragment();
        mStoreDetailsFragment.addListener(this);

        mPurchaseHistoryFragment = new PurchaseHistoryFragment();
        mPurchaseHistoryFragment.addListener(this);

        mUserAddCoinsFragment = new UserAddCoinsFragment();
        mUserAddCoinsFragment.addListener(this);

        mUserWalletFragment = new UserWalletFragment();
        mUserWalletFragment.addListener(this);

        mConfirmationFragment = new ConfirmationFragment();
        mConfirmationFragment.addListener(this);

        mOrderSuccessfullFragment = new OrderSuccessfullFragment();
        mOrderSuccessfullFragment.addListener(this);

        mPurchaseHistoryItemFragment = new PurchaseHistoryItemFragment();
        mPurchaseHistoryItemFragment.addListener(this);

        mProgressDialog = new ProgressDialog(this);

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_USER_CONFIRM_ORDER);
        intentFilter.addAction(Constants.NOTIFY_WALLET_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_REVIEW_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_GET_LOCATION);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, intentFilter);


        checkLocation();

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getLocation(getApplicationContext(), 0);
            //mProgressDialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();

        // showPlacesList();
    }

    @Override
    public void addFragment(int screenId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (screenId) {

            case Constants.SCREEN_USER:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mUserViewPagerFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_STORE_DETAILS:

                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mStoreDetailsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_PURCHASE_DETAILS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mPurchaseHistoryFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_ADD_COINS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mUserAddCoinsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_WALLET:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mUserWalletFragment);

                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_CONFIRMATION:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mConfirmationFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_ORDER_SUCCESSFULL:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mOrderSuccessfullFragment);
                fragmentTransaction.commit();
                break;
            case Constants.SCREEN_PURCHASE_HISTORY_ITEM:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mPurchaseHistoryItemFragment);
                fragmentTransaction.commit();
                break;
            default:
                break;


        }
    }

    @Override
    public void passStoreDetails(int screenId, StoreDetails storeDetails) {

        if (mStoreDetailsFragment != null) {
            mStoreDetailsFragment.selectedStore(storeDetails);
        }
        addFragment(Constants.SCREEN_STORE_DETAILS);

    }

    @Override
    public void showNavigationDrawer() {
        int wallet = getWallet();
        mMenuItemWallet.setTitle("Wallet " + wallet);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void showPurchaseHistory(Order order) {
        if (order != null && mPurchaseHistoryItemFragment != null) {
            mPurchaseHistoryItemFragment.setOrderDetails(order);
            addFragment(Constants.SCREEN_PURCHASE_HISTORY_ITEM);
        }
    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }

    @Override
    public void addPopup(DishesDetails dishesDetails) {
        if (dishesDetails != null) {
            showPopup(dishesDetails);
        }
    }

    @Override
    public void loadConformationFragment(List<DishesDetails> dishesDetails) {
        mConfirmationFragment.setConfirmationDetails(dishesDetails);
        addFragment(Constants.SCREEN_CONFIRMATION);
    }


    private void showPlacesList() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        int selectedLoc = 0,selectedLocIndex =0;
        if(sharedPreferences != null && sharedPreferences.contains(Constants.KEY_LOCATION)){
            selectedLoc = sharedPreferences.getInt(Constants.KEY_LOCATION,0);
        }

        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            places = calls.getLocations();
        }
        if (places != null) {

            Dialog dialog;
            List<String> items = new ArrayList<>();
            for (LocationDetails locationDetails : places) {
                if(selectedLoc != 0
                        && locationDetails.getLocationId() == selectedLoc){
                    selectedLocIndex = places.indexOf(locationDetails);
                }
                items.add(locationDetails.getLocationName());
            }
            mLocationId = places.get(0).getLocationId();
            final String[] placesList = items.toArray(new String[items.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your location: ");
            builder.setSingleChoiceItems(placesList, selectedLocIndex,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mUserSelectedLocation = placesList[which];

                            for (LocationDetails locationDetails : places) {
                                if (locationDetails.getLocationName().equals(mUserSelectedLocation)) {
                                    mLocationId = locationDetails.getLocationId();
                                    flag = 1;
                                    break;
                                }
                            }
                        }
                    });

            builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Your logic when OK button is clicked
                    if (flag == 1) {
                        addLocationTosharedPreferance();
                        LoadDetails();
                    }
                    flag = 0;


                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (sharedPreferences != null
                                    && !sharedPreferences.contains(Constants.KEY_LOCATION)) {

                                addLocationTosharedPreferance();
                                LoadDetails();
                            }
                            flag = 0;


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
                    mProgressDialog.show();
                }
            } else {
                LoadDetails();
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


    private void showPopup(final DishesDetails dishFromApi) {
        if (dishFromApi != null) {
            // ...Irrelevant code for customizing the buttons and title
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_popup);

            TextView storeName = dialog.findViewById(R.id.popup_store_name);
            TextView itemName = dialog.findViewById(R.id.popup_item_name);
            TextView itemPrice = dialog.findViewById(R.id.popup_item_price);
            final TextView itemCount = dialog.findViewById(R.id.popup_dish_item_count);
            ImageButton addBt = dialog.findViewById(R.id.popup_im_add);
            ImageButton removeBt = dialog.findViewById(R.id.popup_im_rem);

            TextView btBuy = dialog.findViewById(R.id.popup_buy);
            TextView btCancel = dialog.findViewById(R.id.popup_cancel);
            storeName.setText("Store Id " + dishFromApi.getRetailId());
            itemName.setText(dishFromApi.getItemName());
            itemPrice.setText("₹ " + dishFromApi.getItemPrice());


            addBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    counter = counter + 1;
                    dishFromApi.setCounter(counter);
                    itemCount.setText(String.valueOf(counter));

                }
            });
            removeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter > 0) {
                        counter--;
                    }
                    dishFromApi.setCounter(counter);
                    itemCount.setText(String.valueOf(counter));

                }
            });
            btBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter = 0;
                    if (sharedPreferences != null) {
                        if (!itemCount.getText().toString().isEmpty()) {

                            int numberOfItems = Integer.parseInt(itemCount.getText().toString());

                            int userid = sharedPreferences.getInt(Constants.KEY_ID, 0);
                            int stock = dishFromApi.getItemStock();
                            if (stock >= numberOfItems) {
                                int total = numberOfItems * dishFromApi.getItemPrice();
                                int wallet = sharedPreferences.getInt(Constants.KEY_WALLET, 0);
                                if (wallet >= total && total > 0) {
                                    Order orderDetails = new Order();
                                    orderDetails.setUser_id(userid);
                                    orderDetails.setRetail_id(dishFromApi.getRetailId());
                                    orderDetails.setOrder_item_count(numberOfItems);
                                    orderDetails.setOrder_value(total);
                                    orderDetails.setOrder_items_string("" + dishFromApi.getMenuId());
                                    if (isNetworkAvailable()) {
                                        RetrofitNetworksCalls calls = DataSingleton
                                                .getInstance().getRetrofitNetworksCallsObject();
                                        if (calls != null) {
                                            calls.confirmOrder(getApplicationContext(), orderDetails);
                                        }
                                        showProgress();
                                        dialog.dismiss();
                                    } else {
                                        showSnackBar();
                                    }
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(mCoordinatorLayout, "Sorry you have no sufficient balance",
                                                    Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            } else{
                                Snackbar snackbar = Snackbar
                                        .make(mCoordinatorLayout, "Exceeded Stock limit",
                                                Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please select dish count ", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter = 0;
                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

        }

    }

    private void getUserWalletDetails() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            calls.getUserWalletDetails(this);
        }
    }

    private void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showProgress() {
        if (mProgressDialog != null) {

            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
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
        drawer.addDrawerListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        mProfileName = hView.findViewById(R.id.tv_profilename);
        if (sharedPreferences != null) {
            String name = sharedPreferences.getString(Constants.KEY_USER_NAME, "");
            mProfileName.setText(name);
        }
        mMenuList = navigationView.getMenu();
        mMenuItemWallet = mMenuList.findItem(R.id.nav_wallet);
        int wallet = getWallet();
        mMenuItemWallet.setTitle("Wallet " + wallet);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private int getWallet() {
        SharedPreferences sharedpreferences
                = getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                        Context.MODE_PRIVATE);
        int wallet = sharedpreferences.getInt(Constants.KEY_WALLET, 0);
        return wallet;
    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mUserAddCoinsFragment.isVisible()
                || mUserWalletFragment.isVisible()
                || mConfirmationFragment.isVisible()
                || mStoreDetailsFragment.isVisible()
                || mPurchaseHistoryItemFragment.isVisible()
                || mPurchaseHistoryFragment.isVisible()) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            UserViewPagerFragment userViewPagerFragment = new UserViewPagerFragment();
            userViewPagerFragment.addListener(this);
            fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                    userViewPagerFragment);
            fragmentTransaction.commit();

        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
            //addFragment(Constants.SCREEN_USER_WALLET);

        } else if (id == R.id.nav_logout) {
            finishAffinity();
        } else if (id == R.id.nav_places_list) {
            showPlacesList();
        } else if (id == R.id.nav_purchase_history_list) {
            addFragment(Constants.SCREEN_PURCHASE_DETAILS);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showDialog(UserReview userReview) {
        if (userReview != null) {
            mUserReview = userReview;
            reviewId = userReview.getReviewId();
            new AppRatingDialog.Builder()
                    .setPositiveButtonText("Submit")
                    .setNegativeButtonText("Cancel")
                    .setNeutralButtonText("Later")
                    .setNoteDescriptions(Arrays.asList("Very Bad",
                            "Not good", "Quite ok",
                            "Very Good",
                            "Excellent !!!"))
                    .setDefaultRating(1)
                    .setTitle("Rate your YummY Taste")
                    .setDescription("Please select some stars")
                    .setDefaultComment("It is delicious !")
                    .setStarColor(R.color.colorAccent)
                    .setNoteDescriptionTextColor(R.color.text)
                    .setTitleTextColor(R.color.light_tab)
                    .setDescriptionTextColor(R.color.light_tab)
                    .setHint("Please write your comment here ...")
                    .setHintTextColor(R.color.tab_color)
                    .setCommentTextColor(R.color.background)
                    .setCommentBackgroundColor(R.color.rvbackground)
                    .setWindowAnimation(R.style.MyDialogFadeAnimation)
                    .create(UserHomeActivity.this)
                    .show();

        }

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("Please wait.....");
            mProgressDialog.show();
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                calls.updateReview(i, getApplicationContext(), reviewId);
            }
        }


    }

    private void removeItemsFromReviewList() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            RetrofitNetworksCalls calls = DataSingleton
                    .getInstance().getRetrofitNetworksCallsObject();
            if (calls != null) {
                calls.updateReviewItemsList(mUserReview, getApplicationContext());
            }
        }
    }

    @Override
    public void onNegativeButtonClicked() {
    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        int wallet = getWallet();
        mMenuItemWallet.setTitle("Wallet " + wallet);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
