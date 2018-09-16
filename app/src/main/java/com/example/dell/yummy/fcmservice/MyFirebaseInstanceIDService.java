package com.example.dell.yummy.fcmservice;

import android.util.Log;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.dbhandler.DbHandler;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        super.onTokenRefresh();;
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refreshed token:", refreshedToken);
        DbHandler dbHandler = new DbHandler(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.COLOUM_USER_TOKEN, refreshedToken);
        dbHandler.insertLoginDetails(hashMap);
        dbHandler.close();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }

}
