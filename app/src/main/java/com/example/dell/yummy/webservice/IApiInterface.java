package com.example.dell.yummy.webservice;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiInterface {

    //public static final String BASE_URL = "http://192.168.43.254:8080/Yummy_Service/api/";
    public static final String BASE_URL
            ="https://fc6b9080-76cb-41df-b8ff-bb3f0f1dca27.mock.pstmn.io/Yummy_Service/api/";


//    @Headers("Content-Type: application/json")
//    @Multipart
//    @FormUrlEncoded


    @POST("user/login")
    Call<UserResult> userLogin(@Body UserResult userResult);

    @GET("retail/getstores")
    Call<List<StoreDetails>> getStores();


    @POST("user/register")
    Call<RegistrationResult> register(@Body RegistrationResult registrationResult);

    @GET("menu/getstoremenu/{id}")
    Call<List<DishesDetails>> getStoreMenu(@Path("id") int id);

    @GET("menu/getmenu")
    Call<List<DishesDetails>> getallMenu();

    @GET("menu/getmenu/{id}")
    Call<DishesDetails> getEachDishDetails(@Path("id") int id);

    @GET("order/getstoreorders/{id}")
    Call<List<TransactionDetails>> getTransactionDetails(@Path("id") int id);

    @POST("order/makeorder")
    Call<Order> getOrderDetails(@Body Order orderDetails);


}
