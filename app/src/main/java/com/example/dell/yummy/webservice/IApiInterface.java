package com.example.dell.yummy.webservice;

import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.RegistrationResult;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.model.TransactionDetails;
import com.example.dell.yummy.model.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiInterface {

    public static final String BASE_URL = "http://192.168.1.112:8080/Yummy_Service/api/";
//    public static final String BASE_URL
//            ="https://5d79daeb-3481-4889-ab12-08b480ce1963.mock.pstmn.io/Yummy_Service/api/";


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
