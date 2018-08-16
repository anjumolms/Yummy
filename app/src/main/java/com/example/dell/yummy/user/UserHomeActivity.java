package com.example.dell.yummy.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.DishesDetails;
import com.example.dell.yummy.user.store.ConfirmationFragment;
import com.example.dell.yummy.user.store.StoreDetailsFragment;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.StoreDetails;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentListener ,RatingDialogListener{

    private UserViewPagerFragment mUserViewPagerFragment;
    private StoreDetailsFragment mStoreDetailsFragment;
    private PaymentDetailsFragment mPaymentDetailsFragment;
    private UserAddCoinsFragment mUserAddCoinsFragment;
    private UserWalletFragment mUserWalletFragment;
    private ConfirmationFragment mConfirmationFragment;
    private TextView mProfileName;
    private FrameLayout mFrameLayout;
    private List<StoreDetails> mStoreDetails;
    private int coins;
    public static int userid;

    DishesDetails dishFromApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
        }
        mFrameLayout = findViewById(R.id.fl_userhome_fragment_container);

        setupNavigationDrawer();


        mUserViewPagerFragment = new UserViewPagerFragment();
        mUserViewPagerFragment.addListener(this);
        mUserViewPagerFragment.setStoreDetails(mStoreDetails);

        mStoreDetailsFragment = new StoreDetailsFragment();
        mStoreDetailsFragment.addListener(this);

        mPaymentDetailsFragment = new PaymentDetailsFragment();
        mPaymentDetailsFragment.addListener(this);

        mUserAddCoinsFragment = new UserAddCoinsFragment();
        mUserAddCoinsFragment.addListener(this);

        mUserWalletFragment = new UserWalletFragment();
        mUserWalletFragment.addListener(this);

        mConfirmationFragment = new ConfirmationFragment();
        mConfirmationFragment.addListener(this);

        addFragment(Constants.SCREEN_USER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserViewPagerFragment.setStoreDetails(mStoreDetails);
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

            case Constants.SCREEN_PAYMENT_DETAILS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mPaymentDetailsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_ADD_COINS:
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mUserAddCoinsFragment);
                fragmentTransaction.commit();
                break;

            case Constants.SCREEN_USER_WALLET:

                Bundle bundle = new Bundle();
                bundle.putInt("Key2", coins);
                mUserWalletFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                        mUserWalletFragment);

                fragmentTransaction.commit();
                break;

                case Constants.SCREEN_CONFIRMATION:
                    fragmentTransaction.replace(R.id.fl_userhome_fragment_container,
                            mConfirmationFragment);
                    fragmentTransaction.commit();
                    break;


            default:
                break;


        }
    }

    @Override
    public void passStoreDetails(int screenId, StoreDetails storeDetails) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("key", storeDetails);
        mStoreDetailsFragment.setArguments(bundle);

        addFragment(Constants.SCREEN_STORE_DETAILS);

    }

    @Override
    public void addPopup(DishesDetails dishesDetails) {
        if (dishesDetails != null) {
            int menuId = dishesDetails.getMenuId();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                    .build();

            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<DishesDetails> call = iApiInterface.getEachDishDetails(3);


            call.enqueue(new Callback<DishesDetails>() {
                @Override
                public void onResponse(Call<DishesDetails> call,
                                       Response<DishesDetails> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            dishFromApi = response.body();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    response.code()
                                            + response.message(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    showPopup(dishFromApi);

                }

                @Override
                public void onFailure(Call<DishesDetails> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

    @Override
    public void loadConformationFragment(List<DishesDetails> dishesDetails) {
        mConfirmationFragment.setConfirmationDetails(dishesDetails);
        addFragment(Constants.SCREEN_CONFIRMATION);
    }

    private void showPopup(DishesDetails dishFromApi) {
        if (dishFromApi != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Store Id " + dishFromApi.getRetailId());
            builder.setMessage(dishFromApi.getItemName() + "       "
                    + dishFromApi.getItemPrice() + " rs");
            builder.setCancelable(false);
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),
                            "You've choosen to buy", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Buy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addFragment(Constants.SCREEN_PAYMENT_DETAILS);
                }
            });

            builder.show();

        }
    }


    private void setupNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        mProfileName = hView.findViewById(R.id.tv_profilename);
        Intent myIntent = getIntent();

        if (myIntent != null) {

            String fName = myIntent.getStringExtra("Key1");
            coins = myIntent.getIntExtra("Key2", 0);
            userid = myIntent.getIntExtra("Key3",0);

            mStoreDetails = (List<StoreDetails>) myIntent.getSerializableExtra("KeyStoreList");

            if (fName != null && !fName.isEmpty()) {

                //mProfileName.setText(fName);
            }
        }
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
      else if(mUserAddCoinsFragment.isVisible()){
            addFragment(Constants.SCREEN_USER);
       }
       else {
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

    public void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setTitle("Rate your YummY Taste")
                .setDescription("Please select some stars and give your feedback")
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
    @Override
    public void onPositiveButtonClicked(int i, String s) {

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
}
