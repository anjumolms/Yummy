package com.example.dell.yummy;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dell.yummy.user.store.UserStoresAdapter;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitClient;
import com.example.dell.yummy.webservice.StoreDetails;
import com.skyfishjy.library.RippleBackground;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    List<StoreDetails> storeDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.tab_color));
        }


        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.INTERNET},
                    101);
        }else{

            getStoreDetails();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getStoreDetails();

            }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat
                        .shouldShowRequestPermissionRationale(SplashActivity.this,
                        Manifest.permission.INTERNET)) {
                    //Show an explanation to the user *asynchronously*
                    ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.INTERNET}, 101);
                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }
    private void getStoreDetails() {

        Retrofit retrofit = RetrofitClient.getClient();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<StoreDetails>> call = iApiInterface.getStores();

        call.enqueue(new Callback<List<StoreDetails>>() {

            @Override
            public void onResponse(Call<List<StoreDetails>> call,
                                   Response<List<StoreDetails>> response) {

                if (response != null) {
                    if (response.code() == 200) {
                        storeDetails = response.body();
                    } else {
                        Toast.makeText(getApplicationContext(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }
                if (storeDetails != null) {

                    Intent mySuperIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mySuperIntent.putExtra("KeyStoreList", (Serializable) storeDetails);
                    startActivity(mySuperIntent);
                }

            }

            @Override
            public void onFailure(Call<List<StoreDetails>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
