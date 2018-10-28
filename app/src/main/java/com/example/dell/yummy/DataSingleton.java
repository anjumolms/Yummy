package com.example.dell.yummy;

import android.content.Context;

import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
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

    public Retrofit getRetrofitInstancewithOkHttp(OkHttpClient okHttpClient) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(IApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
//            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(IApiInterface.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

//    public Retrofit getRetrofitInstance() {
//        if (mRetrofit == null) {
//            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(IApiInterface.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return mRetrofit;
//    }

    public RetrofitNetworksCalls getRetrofitNetworksCallsObject() {
        if (mRetrofitNetworksCalls == null) {
            mRetrofitNetworksCalls = new RetrofitNetworksCalls();
        }
        return mRetrofitNetworksCalls;
    }



}
