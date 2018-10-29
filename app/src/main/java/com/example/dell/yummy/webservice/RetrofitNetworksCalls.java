package com.example.dell.yummy.webservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.model.LocationDetails;
import com.example.dell.yummy.model.Order;
import com.example.dell.yummy.model.RegisterStore;
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.model.RetailerMenu;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.model.User;
import com.example.dell.yummy.model.UserDetails;
import com.example.dell.yummy.model.UserResult;
import com.example.dell.yummy.model.UserReview;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitNetworksCalls {
    private List<StoreDetails> storeDetails;
    private List<DishesDetails> dishesDetails;
    private List<UserReview> mUserReviewList;
    private List<Order> mTransactionDetails;
    private List<Order> mConfirmOrderList = new ArrayList<>();
    private List<DishesDetails> mRetailordishesList = new ArrayList<>();
    private UserDetails mUserDetails;
    private RetailerDetails mRetailerDetails;
    private Order mOrders;
    private List<Order> mConfirmedOrders;
    private List<Order> allTransactions;
    private List<LocationDetails> mPlaces;
    private List<LocationDetails> mGetAllLocationDetails;
    private List<Order> mPurchaseHistory;
    private List<RetailerDetails> allRetailerDetails;
    private User userByNumer;

    //TODO ismmodification
    public static String getAuthToken() {
        byte[] data = new byte[0];
        try {
            data = (Constants.AUTHUSERNAME + ":" + Constants.AUTHPASS).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

    public void getStoreDetails(final Context context, int locationId) {
        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<StoreDetails>> call = iApiInterface.getStores(locationId);

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
                            Intent intent = new Intent(Constants.NOTIFY_STORE_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_STORE_DETAILS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context,
                                "response is null", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<StoreDetails>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_STORE_DETAILS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void getDishDetails(final Context context, int location_id) {
        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<DishesDetails>> call = iApiInterface.getallMenu(location_id);


            call.enqueue(new Callback<List<DishesDetails>>() {
                @Override
                public void onResponse(Call<List<DishesDetails>> call,
                                       Response<List<DishesDetails>> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            dishesDetails = response.body();

                            Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else if (response.code() == 204) {

                            Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context,
                                    "Response failed", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context,
                                "Response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DishesDetails>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_DISH_DETAILS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public void getReviewDetails(final Context context) {
        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);

            SharedPreferences sharedPreferences = context
                    .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                            Context.MODE_PRIVATE);
            int id = 0;
            if (sharedPreferences != null) {
                id = sharedPreferences.getInt(Constants.KEY_ID, 0);
            }

            Call<List<UserReview>> call = iApiInterface.getUserReview(id);


            call.enqueue(new Callback<List<UserReview>>() {
                @Override
                public void onResponse(Call<List<UserReview>> call,
                                       Response<List<UserReview>> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            mUserReviewList = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_REVIEW_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_REVIEW_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_REVIEW_DETAILS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, "Response is null",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserReview>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_REVIEW_DETAILS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public void getTransactionPendingOrders(final Context context, int id) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<Order>> call = iApiInterface
                    .getTransactionPendingOrders(id);

            call.enqueue(new Callback<List<Order>>() {


                @Override
                public void onResponse(Call<List<Order>> call,
                                       Response<List<Order>> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            mTransactionDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_TRANSACTION_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {

                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    public void getConfirmedOrders(final Context context, int id) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<Order>> call = iApiInterface
                    .getConfirmedOrders(id);

            call.enqueue(new Callback<List<Order>>() {


                @Override
                public void onResponse(Call<List<Order>> call,
                                       Response<List<Order>> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            mConfirmedOrders = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_CONFIRMED_TRANSACTIONS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {

                            Intent intent = new Intent(Constants.NOTIFY_CONFIRMED_TRANSACTIONS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_CONFIRMED_TRANSACTIONS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, "Response is null",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_CONFIRMED_TRANSACTIONS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    public void getAllTransactionDetails(final Context context, int id) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<Order>> call = iApiInterface
                    .getAllTransactionDetails(id);

            call.enqueue(new Callback<List<Order>>() {


                @Override
                public void onResponse(Call<List<Order>> call,
                                       Response<List<Order>> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            allTransactions = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_ALL_TRANSACTIONS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {

                            Intent intent = new Intent(Constants.NOTIFY_ALL_TRANSACTIONS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_ALL_TRANSACTIONS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, "Response is null",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_ALL_TRANSACTIONS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    public List<Order> getAllTransactionList() {
        return allTransactions;
    }

    public void resetTransactionList() {
        if (allTransactions != null) {
            allTransactions.clear();
        }
    }

    public List<Order> getConfirmedOrdersList() {
        return mConfirmedOrders;
    }

    public void addOrderConfirmedTransaction(Order transactionDetails) {
        mConfirmOrderList.add(transactionDetails);
    }

    public void getRetailerMenuList(final Context context, final int isFromListUpdate) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                        Context.MODE_PRIVATE);
        int id = 0;
        if (sharedPreferences != null) {
            id = sharedPreferences.getInt(Constants.KEY_ID, 0);
        }
        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<DishesDetails>> call = iApiInterface
                    .getStoreMenu(id);

            call.enqueue(new Callback<List<DishesDetails>>() {
                @Override
                public void onResponse(Call<List<DishesDetails>> call,
                                       Response<List<DishesDetails>> response) {
                    if (response != null && response.code() == 200) {


                        mRetailordishesList = response.body();
                        Intent intent = new Intent(Constants.NOTIFY_RETAILOR_DISH_DETAILS);
                        intent.putExtra(Constants.KEY_LIST_UPDATE, isFromListUpdate);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                        if (isFromListUpdate == 1 && mRetailordishesList != null) {
//                            mRetailordishesList.clear();
//                        }
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DishesDetails>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    public void updateReview(int rating, final Context context, int reviewId) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);

        Call<String> call = iApiInterface.updateReview(reviewId, rating);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {

                if (response != null) {
                    if (response.code() == 200) {
                        if (response.body()
                                .equalsIgnoreCase("Review Updated")) {

                            Toast.makeText(context, "Review Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Constants.NOTIFY_REVIEW_UPDATED);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        } else{
                            Toast.makeText(context, "Response failed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Constants.NOTIFY_REVIEW_UPDATED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_REVIEW_UPDATED_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Intent intent = new Intent(Constants.NOTIFY_REVIEW_UPDATED_ERROR);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public List<StoreDetails> getStoreDetailsList() {
        return storeDetails;
    }

    public List<DishesDetails> getDishDetailsList() {
        return dishesDetails;
    }

    public List<UserReview> getUserReviewDetails() {
        return mUserReviewList;
    }

    public List<DishesDetails> getRetailerDishDetails() {
        return mRetailordishesList;
    }

    public void resetmRetailordishesList() {
        if (mRetailordishesList != null) {
            mRetailordishesList.clear();
        }
    }


    public List<Order> getTransactionList() {
        return mTransactionDetails;
    }

    public void resetRetailerTransactionList() {
        if (mTransactionDetails != null) {
            mTransactionDetails.clear();
        }
    }

    public List<Order> getConfirmOrderList() {
        return mConfirmOrderList;
    }

    public void addItemsToList(RetailerMenu retailerMenu, final Context context) {

        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);

        Call<String> call = iApiInterface.addItem(retailerMenu);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {

                if (response != null) {
                    if (response.code() == 200) {
                        if (response.body()
                                .equalsIgnoreCase("Item Added Successfully")) {

                            Toast.makeText(context, "Item added Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Constants.NOTIFY_ITEM_ADDED);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }

                    } else {
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Constants.NOTIFY_ITEM_ADDED_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                } else {
                    Intent intent = new Intent(Constants.NOTIFY_ITEM_ADDED_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Intent intent = new Intent(Constants.NOTIFY_ITEM_ADDED_ERROR);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void getUserDetails(final Context context, int loginId) {


        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<UserDetails> call = iApiInterface.getUserDetails(loginId);


            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call,
                                       Response<UserDetails> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            mUserDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_USER_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {

                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public void getRetailerDetails(final Context context, int loginId) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<RetailerDetails> call = iApiInterface.getRetailerDetails(loginId);


            call.enqueue(new Callback<RetailerDetails>() {
                @Override
                public void onResponse(Call<RetailerDetails> call,
                                       Response<RetailerDetails> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            mRetailerDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_RETAILER_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RetailerDetails> call, Throwable t) {

                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public UserDetails getUsersDetails() {
        return mUserDetails;
    }

    public RetailerDetails getmRetailerDetails() {
        return mRetailerDetails;
    }

    public void updateRetailerMenuItem(final Context context,
                                       DishesDetails dishesDetails) {

        if (dishesDetails != null) {
            OkHttpClient client = createOkHttpClient(context);
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(IApiInterface.BASE_URL).build();
            if (retrofit != null) {
                IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
                Call<String> call = iApiInterface.updateMenuItem(dishesDetails);


                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call,
                                           Response<String> response) {

                        if (response != null) {
                            if (response.code() == 200) {
                                String data = response.body();
                                if (data.equalsIgnoreCase("Item Updated Successfully")) {
                                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_UPDATED);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                } else {
                                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                }

                            } else {
                                Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                Toast.makeText(context, response.code()
                                        + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Response is null",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Intent intent = new Intent(Constants.NOTIFY_UPDATE_ITEM_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
    }

    public void deleteItemFromList(final Context context, ArrayList<Integer> list) {

        if (list != null) {
            OkHttpClient client = createOkHttpClient(context);
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(IApiInterface.BASE_URL).build();
            if (retrofit != null) {
                IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
                Call<String> call = iApiInterface.deleteItemList(list);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call,
                                           Response<String> response) {

                        if (response != null) {
                            if (response.code() == 200) {
                                String data = response.body();
                                 if(data.equalsIgnoreCase("Items Deleted Successfully")){
                                     Toast.makeText(context,"Deleted Successfully" , Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_DELETED);
                                     LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                 }else{
                                     Toast.makeText(context,"Failed" , Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_DELETED_ERROR);
                                     LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                 }

                            } else {
                                Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_DELETED_ERROR);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                Toast.makeText(context, response.code()
                                        + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_DELETED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Response is null",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Intent intent = new Intent(Constants.NOTIFY_LIST_ITEM_DELETED_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }

    }

    public void confirmOrder(final Context context, Order orderDetails) {


        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {

            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<Order> call = iApiInterface
                    .getOrderDetails(orderDetails);

            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (response != null && response.code() == 200) {
                        Order result = response.body();
                        //TODO add order success variable
                        if (result != null
                                && result.getOrder_status()
                                .equalsIgnoreCase("ORDER_SUCCESSFULL")) {
                            Toast.makeText(context,
                                    "Order successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Constants.NOTIFY_USER_CONFIRM_ORDER);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_USER_CONFIRM_ORDER_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context,
                                    "Order Failed", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_USER_CONFIRM_ORDER_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context,
                                "Response is null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_USER_CONFIRM_ORDER_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, call.toString()
                            + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getLocation(final Context context, final int firstCalling) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<List<LocationDetails>> call = service.getLocations(getAuthToken());
            call.enqueue(new Callback<List<LocationDetails>>() {
                @Override
                public void onResponse(Call<List<LocationDetails>> call,
                                       Response<List<LocationDetails>> response) {
                    if (response != null && response.code() == 200) {

                        mPlaces = response.body();
                        if (mPlaces != null) {
                            Intent intent = new Intent(Constants.NOTIFY_GET_LOCATION);
                            intent.putExtra("Flag", firstCalling);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else if (response.code() == 204) {
                            Toast.makeText(context, "Invalid",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<LocationDetails>> call, Throwable t) {
                    Toast.makeText(context,
                            "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public List<LocationDetails> getLocations() {
        return mPlaces;
    }

    public void getUserWalletDetails(final Context context) {
        final SharedPreferences sharedpreferences
                = context.getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                Context.MODE_PRIVATE);

        int id = sharedpreferences.getInt(Constants.KEY_ID, 0);
        int role = sharedpreferences.getInt(Constants.KEY_ROLE, 0);


        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            Call<String> call = null;
            IApiInterface service = retrofit.create(IApiInterface.class);
            if (role == 1) {
                call = service.getWallet(id);
            }
            if (role == 2) {
                call = service.getRetailWallet(id);
            }

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null && response.code() == 200) {

                        if (response.body() != null) {
                            String wallet = response.body();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt(Constants.KEY_WALLET,
                                    Integer.parseInt(wallet));
                            editor.commit();

                            Intent intent = new Intent(Constants.NOTIFY_WALLET_UPDATED);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else if (response.code() == 204) {
                            Intent intent = new Intent(Constants.NOTIFY_WALLET_UPDATED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Invalid email or password",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_WALLET_UPDATED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_WALLET_UPDATED_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context,
                            "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public void updateReviewItemsList(UserReview userReview, Context context) {
        if (mUserReviewList != null && !mUserReviewList.isEmpty()) {
            if (mUserReviewList.contains(userReview)) {
                mUserReviewList.remove(userReview);
                Intent intent = new Intent(Constants.NOTIFY_REVIEW_DETAILS);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }
    }

    public void addStoreDetails(final Context context, final RegisterStore registerStore) {

        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<String> call = service.addStoreDetails(registerStore);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            Toast.makeText(context, response.body()
                                    , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Constants.NOTIFY_STORE_ADDED);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else if (response.code() == 409) {
                            Intent intent = new Intent(Constants.NOTIFY_STORE_ADDED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Already existing store id/store name",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_STORE_ADDED_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Store registration failed. Try after some time !!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_STORE_ADDED_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Log.w("LoginFragment ", "Retrofit object is null ");
        }

    }

    public void updateToken(final Context context, UserResult userResult) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<String> call = service.updateToken(userResult);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Toast.makeText(context, response.body()
                                    , Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public void addPlaces(LocationDetails locationDetails, final Context context) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<String> call = service.addLocation(locationDetails);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Toast.makeText(context, response.body()
                                    , Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }


    public void getAllLocations(final Context context) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<List<LocationDetails>> call = service.getAllLocations();

            call.enqueue(new Callback<List<LocationDetails>>() {
                @Override
                public void onResponse(Call<List<LocationDetails>> call,
                                       Response<List<LocationDetails>> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            mGetAllLocationDetails = response.body();

                            Intent intent = new Intent(Constants.NOTIFY_GET_ALL_LOCATIONS_LIST);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_GET_ALL_LOCATIONS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_GET_ALL_LOCATIONS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<LocationDetails>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_GET_ALL_LOCATIONS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public List<LocationDetails> getmGetAllLocationDetails() {
        return mGetAllLocationDetails;
    }

    public void updateDelivery(int orderId, final Context context) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<String> call = service.updateDelivery(orderId);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(Constants.NOTIFY_UPDATE_DELIVERY);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.body()
                                    , Toast.LENGTH_SHORT).show();


                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_UPDATE_DELIVERY_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void getTransactionOrders(final Context context, int orderId) {


        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);

            Call<Order> call = service.getTransactionOrders(orderId);

            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call,
                                       Response<Order> response) {
                    if (response != null && response.code() == 200) {

                        if (response.body() != null) {
                            mOrders = response.body();
                            Toast.makeText(context, "Orders list", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Constants.NOTIFY_TRANSACTION_ORDER);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {

                            Intent intent = new Intent(Constants.NOTIFY_TRANSACTION_ORDER_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Constants.NOTIFY_TRANSACTION_ORDER_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_TRANSACTION_ORDER_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context,
                            "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public Order getOrderInfo() {
        return mOrders;
    }

    public void updateOrderInfo() {
        mOrders = null;
    }


    public void getPurchaseHistory(final Context context) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            SharedPreferences sharedPreferences = context
                    .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                            Context.MODE_PRIVATE);
            int id = 0;
            if (sharedPreferences != null) {
                id = sharedPreferences.getInt(Constants.KEY_ID, 0);
            }
            Call<List<Order>> call = service.getPurchaseHistory(id);

            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call,
                                       Response<List<Order>> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            mPurchaseHistory = response.body();

                            Intent intent = new Intent(Constants.NOTIFY_GET_PURCHASE_HISTORY);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_GET_PURCHASE_HISTORY_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_GET_PURCHASE_HISTORY_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_GET_PURCHASE_HISTORY_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public List<Order> getPurchaseHistoryList() {
        return mPurchaseHistory;
    }


    public void getAllStoreDetails(final Context context) {


        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            Call<List<RetailerDetails>> call = iApiInterface.getAllStores();

            call.enqueue(new Callback<List<RetailerDetails>>() {

                @Override
                public void onResponse(Call<List<RetailerDetails>> call,
                                       Response<List<RetailerDetails>> response) {

                    if (response != null) {
                        if (response.code() == 200) {
                            allRetailerDetails = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_ALL_STORE_DETAILS);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_ALL_STORE_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_ALL_STORE_DETAILS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context,
                                "response is null", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<RetailerDetails>> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_ALL_STORE_DETAILS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public List<RetailerDetails> getAllStoreListCollection() {
        return allRetailerDetails;
    }

    public void addAdmin(final Context context, String strUserName, String strPassword) {

        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);
            Call<String> call = service.addadmin(strUserName, strPassword);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Toast.makeText(context, response.body()
                                    , Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Constants.NOTIFY_ADD_ADMIN);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else if (response.code() == 204) {
                            Intent intent = new Intent(Constants.NOTIFY_ADD_ADMIN_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, "Admin already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Response is null",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void callRefundApi(final Context context, int amount,
                              int userId, int retailId) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);

            Call<String> call = service.callRefundApi(userId, retailId, amount);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Intent intent = new Intent(Constants.NOTIFY_REFUND);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_REFUND_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_REFUND_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_REFUND_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void getUsersByNumber(final Context context, String number) {

        OkHttpClient client = createOkHttpClient(context);

        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstancewithOkHttp(client);
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);

            Call<User> call = service.getUserByNumber(number);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call,
                                       Response<User> response) {
                    if (response != null) {
                        if (response.code() == 200) {
                            userByNumer = response.body();
                            Intent intent = new Intent(Constants.NOTIFY_GET_USER_BY_NUMBER);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_GET_USER_BY_NUMBER_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_GET_USER_BY_NUMBER_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_GET_USER_BY_NUMBER_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public User getUserDetailsByNuber() {
        return userByNumer;
    }

    public void updateProfileDetails(final Context context,
                                     RetailerDetails mRetailerDetails) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);

            Call<String> call = service.updateRetailerProfile(mRetailerDetails);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Intent intent = new Intent(Constants.NOTIFY_PROFILE_DETAILS_UPDATE);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_PROFILE_DETAILS_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_PROFILE_DETAILS_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_PROFILE_DETAILS_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public void updateUserWallet(final Context context, int userAmount) {
        OkHttpClient client = createOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(IApiInterface.BASE_URL).build();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SHARED_PREFERANCE_LOGIN_DETAILS,
                        Context.MODE_PRIVATE);
        int id = 0;
        if (sharedPreferences != null) {
            id = sharedPreferences.getInt(Constants.KEY_ID, 0);
        }

        if (retrofit != null) {
            IApiInterface service = retrofit.create(IApiInterface.class);

            Call<String> call = service.updateUserWallet(id, userAmount);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call,
                                       Response<String> response) {
                    if (response != null) {
                        if (response.code() == 200) {

                            Intent intent = new Intent(Constants.NOTIFY_UPDATE_USER_WALLET);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(Constants.NOTIFY_UPDATE_USER_WALLET_ERROR);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            Toast.makeText(context, response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Constants.NOTIFY_UPDATE_USER_WALLET_ERROR);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        Toast.makeText(context, response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Intent intent = new Intent(Constants.NOTIFY_UPDATE_USER_WALLET_ERROR);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public OkHttpClient createOkHttpClient(Context context) {
        // loading CAs from an InputStream
        CertificateFactory certificateFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        InputStream inputStream = context.getResources().openRawResource(R.raw.certnew); //(.crt)
        Certificate certificate = null;
        try {
            certificate = certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            keyStore.setCertificateEntry("ca", certificate);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        // Create a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            trustManagerFactory.init(keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];


        // Create an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        //create Okhttp client
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, x509TrustManager)
                .build();

        return client;

    }
}
