package com.example.dell.yummy.webservice;

import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.LocationDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.RegisterStore;
import com.example.dell.yummy.model.RegistrationResult;
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.model.RetailerMenu;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.model.User;
import com.example.dell.yummy.model.UserDetails;
import com.example.dell.yummy.model.UserResult;
import com.example.dell.yummy.model.UserReview;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiInterface {

    //public static final String BASE_URL = "http://192.168.43.78:8080/Yummy_Service/api/";
    public static final String BASE_URL = "https://yummyaid.ust-global.com/Yummy_Service/api/";
//     public static final String BASE_URL = "https://91864159-07c3-4e28-8056-733d559b17cc.mock.pstmn.io/Yummy_Service/api/";
//    public static final String BASE_URL
//            = "https://9cbf6479-2ac8-4f57-81b0-723bca891763.mock.pstmn.io/Yummy_Service/api/";


//    @Headers("Content-Type: application/json")
//    @Multipart
//    @FormUrlEncoded


    @POST("user/login")
    Call<UserResult> userLogin(@Body UserResult userResult, @Header("Authorization") String authkey);

    @GET("retail/getstores/{location_id}")
    Call<List<StoreDetails>> getStores(@Path("location_id") int location_id, @Header("Authorization") String authkey);

    @POST("user/register")
    Call<RegistrationResult> register(@Body RegistrationResult registrationResult, @Header("Authorization") String authkey);

    @GET("menu/getstoremenu/{id}")
    Call<List<DishesDetails>> getStoreMenu(@Path("id") int id, @Header("Authorization") String authkey);

    @GET("menu/getmenu/{location_id}")
    Call<List<DishesDetails>> getallMenu(@Path("location_id") int location_id, @Header("Authorization") String authkey);

    @GET("menu/getmenu/{id}")
    Call<DishesDetails> getEachDishDetails(@Path("id") int id, @Header("Authorization") String authkey);

    @GET("order/getstorependorders/{retail_id}")
    Call<List<Order>> getTransactionPendingOrders(@Path("retail_id") int retail_id, @Header("Authorization") String authkey);

    @POST("order/makeorder")
    Call<Order> getOrderDetails(@Body Order orderDetails, @Header("Authorization") String authkey);

    @GET("review/getreview/{id}")
    Call<List<UserReview>> getUserReview(@Path("id") int userId, @Header("Authorization") String authkey);

    @GET("review/update/{reviewId}/{rating}")
    Call<String> updateReview(@Path("reviewId") int userId, @Path("rating") int rating, @Header("Authorization") String authkey);

    @POST("menu/putitem")
    Call<String> addItem(@Body RetailerMenu retailerMenu, @Header("Authorization") String authkey);

    @POST("menu/updateitem")
    Call<String> updateMenuItem(@Body DishesDetails dishesDetails, @Header("Authorization") String authkey);

    //    @FormUrlEncoded
    @POST("menu/deleteitemlist")
    Call<String> deleteItemList(@Body ArrayList<Integer> list, @Header("Authorization") String authkey);

    @GET("admin/getlocation")
    Call<List<LocationDetails>> getLocations(@Header("Authorization") String authkey);

    @GET("user/login/{login_id}")
    Call<UserDetails> getUserDetails(@Path("login_id") int login_id, @Header("Authorization") String authkey);

    @GET("retail/login/{login_id}")
    Call<RetailerDetails> getRetailerDetails(@Path("login_id") int login_id, @Header("Authorization") String authkey);

    @GET("user/getwallet/{user_id}")
    Call<String> getWallet(@Path("user_id") int user_id, @Header("Authorization") String authkey);

    @GET("retail/getwallet/{retail_id}")
    Call<String> getRetailWallet(@Path("retail_id") int retail_id, @Header("Authorization") String authkey);

    @GET("order/updatedelivery/{order_id}")
    Call<String> updateDelivery(@Path("order_id") int order_id, @Header("Authorization") String authkey);

    @GET("order/getorders/{order_id}")
    Call<Order> getTransactionOrders(@Path("order_id") int order_id, @Header("Authorization") String authkey);

    @GET("order/getstorecomporders/{retail_id}")
    Call<List<Order>> getConfirmedOrders(@Path("retail_id") int retail_id, @Header("Authorization") String authkey);

    @GET("order/getstoreorders/{retail_id}")
    Call<List<Order>> getAllTransactionDetails(@Path("retail_id") int retail_id, @Header("Authorization") String authkey);

    @POST("retail/putstore")
    Call<String> addStoreDetails(@Body RegisterStore registerStore, @Header("Authorization") String authkey);

    @POST("user/updatetoken")
    Call<String> updateToken(@Body UserResult userResult, @Header("Authorization") String authkey);

    @POST("admin/addlocation")
    Call<String> addLocation(@Body LocationDetails locationDetails, @Header("Authorization") String authkey);

    @GET("admin/getaddlocation")
    Call<List<LocationDetails>> getAllLocations(@Header("Authorization") String authkey);

    @GET("order/getuserorders/{user_id}")
    Call<List<Order>> getPurchaseHistory(@Path("user_id") int user_id, @Header("Authorization") String authkey);

    @GET("retail/getallstores")
    Call<List<RetailerDetails>> getAllStores(@Header("Authorization") String authkey);

    @GET("admin/addadmin/{admin_username}/{admin_pass}")
    Call<String> addadmin(@Path("admin_username") String admin_username,
                          @Path("admin_pass") String admin_pass, @Header("Authorization") String authkey);

    @GET("user/getuserbynumber/{user_phone}")
    Call<User> getUserByNumber(@Path("user_phone") String user_phone, @Header("Authorization") String authkey);

    @GET("retail/refund/{userid}/{retailid}/{amnt}")
    Call<String> callRefundApi(@Path("userid") int userid,
                               @Path("retailid") int retailid,
                               @Path("amnt") int amnt, @Header("Authorization") String authkey);

    @GET("retail/updatestoredetail")
    Call<String> updateRetailerProfile(@Body RetailerDetails mRetailerDetails, @Header("Authorization") String authkey);

    @GET("user/adminloadwallet/{userid}/{amnt}")
    Call<String> updateUserWallet(@Path("userid") int userid, @Path("amnt") int amnt, @Header("Authorization") String authkey);

}
