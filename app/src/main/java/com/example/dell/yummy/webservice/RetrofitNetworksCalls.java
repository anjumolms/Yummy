package com.example.dell.yummy.webservice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.MainActivity;
import com.example.dell.yummy.SplashActivity;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.StoreDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitNetworksCalls {
    private List<StoreDetails> storeDetails;
    private List<DishesDetails> dishesDetails;

    public void getStoreDetails(final Context context) {

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<StoreDetails>> call = iApiInterface.getStores();

            call.enqueue(new Callback<List<StoreDetails>>() {

                @Override
                public void onResponse(Call<List<StoreDetails>> call,
                                       Response<List<StoreDetails>> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            storeDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_STORE_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<List<StoreDetails>> call, Throwable t) {
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    public void getDishDetails(final Context context) {
        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<DishesDetails>> call = iApiInterface.getallMenu();


            call.enqueue(new Callback<List<DishesDetails>>() {
                @Override
                public void onResponse(Call<List<DishesDetails>> call,
                                       Response<List<DishesDetails>> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            dishesDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DishesDetails>> call, Throwable t) {

                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public List<StoreDetails> getStoreDetailsList() {
        return storeDetails;
    }

    public List<DishesDetails> getDishDetailsList() {
        return dishesDetails;
    }
}
