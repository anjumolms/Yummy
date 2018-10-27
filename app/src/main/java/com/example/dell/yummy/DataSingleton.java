package com.example.dell.yummy;

import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSingleton {
    private static DataSingleton sDataSingleton = null;
    private Retrofit mRetrofit;
    private RetrofitNetworksCalls mRetrofitNetworksCalls;

    private DataSingleton() {
    }

    public static DataSingleton getInstance() {
        if (sDataSingleton == null) {
            sDataSingleton = new DataSingleton();
        }
        return sDataSingleton;
    }

    public Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(IApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

    public RetrofitNetworksCalls getRetrofitNetworksCallsObject() {
        if (mRetrofitNetworksCalls == null) {
            mRetrofitNetworksCalls = new RetrofitNetworksCalls();
        }
        return mRetrofitNetworksCalls;
    }
}
