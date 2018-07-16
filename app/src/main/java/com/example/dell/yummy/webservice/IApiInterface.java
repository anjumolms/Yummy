package com.example.dell.yummy.webservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IApiInterface {

    public static final String BASE_URL = "http://192.168.43.254:8080/Yummy_Service/api/";

//    @Headers("Content-Type: application/json")
//    @Multipart
//    @FormUrlEncoded

    @POST("user/login")
    Call<UserResult> userLogin(
            @Body UserResult userResult
    );

    @GET("retail/getstores")
    Call<List<StoreDetails>> getStores();

    @GET("getstores/1")
    Call<List<StoreDetails>> getStoreDishes();

}
