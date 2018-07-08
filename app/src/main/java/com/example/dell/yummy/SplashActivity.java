package com.example.dell.yummy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skyfishjy.library.RippleBackground;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);

        rippleBackground.startRippleAnimation();

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                if(isNetworkAvailable()){
                    Intent mySuperIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mySuperIntent);
                    /* This 'finish()' is for exiting the app when back button pressed
                     *  from Home page which is ActivityHome
                     */
                    rippleBackground.stopRippleAnimation();
                    finish();
                }else{
                            new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("Oops...")
                            .setMessage("Something went wrong!")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Whatever...
                                        }
                                    }).show();
                }
            }
        }, SPLASH_TIME);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
