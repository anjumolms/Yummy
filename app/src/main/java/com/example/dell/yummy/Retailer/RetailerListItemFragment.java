package com.example.dell.yummy.Retailer;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.user.store.UserStoresAdapter;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerListItemFragment extends Fragment implements View.OnClickListener {
    private IRetailerFragmentListener mFragmentListener;
    private RecyclerView mRecyclerView;
    private List<DishesDetails> mRetailordishesList;
    private RetailerListItemAdapter adapter;
    private int counter;
    private ProgressDialog progressDialog;
    private FloatingActionButton actionButton;
    private TextView back;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Constants.NOTIFY_RETAILOR_DISH_DETAILS)) {
                    int intExtra = intent.getIntExtra(Constants.KEY_LIST_UPDATE, 0);
                    updateData(intExtra);
                }
                if (intent.getAction().equals(Constants.NOTIFY_LIST_ITEM_UPDATED)) {
                    listItemUpdated();
                }

                if (intent.getAction().equals(Constants.NOTIFY_LIST_ITEM_DELETED)) {
                    listItemUpdated();
                }

                if (intent.getAction().equals(Constants.NOTIFY_UPDATE_ITEM_ERROR)) {
                    stopProgress();
                    updateList();
                }
            }

        }
    };

    public RetailerListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_list_item,
                container, false);
        initViews(view);
        mRetailordishesList = new ArrayList<>();
        return view;

    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_retailer_list_dish);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        actionButton = view.findViewById(R.id.fb_retailer_item_delete);
        progressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.tv_list_back);
        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_RETAILOR_DISH_DETAILS);
        intentFilter.addAction(Constants.NOTIFY_LIST_ITEM_UPDATED);
        intentFilter.addAction(Constants.NOTIFY_LIST_ITEM_DELETED);
        intentFilter.addAction(Constants.NOTIFY_UPDATE_ITEM_ERROR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);
        actionButton.setOnClickListener(this);
        back.setOnClickListener(this);
        showStoreDetails();

    }

    public void showStoreDetails() {
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null) {
            RetrofitNetworksCalls calls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (calls != null) {
                List<DishesDetails> dishesDetails = calls.getRetailerDishDetails();
                adapter = new RetailerListItemAdapter(getActivity(),
                        dishesDetails, mFragmentListener);
                mRecyclerView.setAdapter(adapter);
            }

        }

    }

    public void addListener(IRetailerFragmentListener retailerFragmentListener) {
        this.mFragmentListener = retailerFragmentListener;
    }

    private void updateData(int intExtra) {
        if (adapter != null) {
            RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                    .getRetrofitNetworksCallsObject();
            if (retrofitNetworksCalls != null) {
                adapter.setData(retrofitNetworksCalls.getRetailerDishDetails());
                adapter.notifyDataSetChanged();
            }
        }

        if (intExtra == 1 && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void stopProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RetrofitNetworksCalls retrofitNetworksCalls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (retrofitNetworksCalls != null) {
            retrofitNetworksCalls.resetmRetailordishesList();
        }
    }

    private void updateList() {
        if (adapter != null) {
            adapter.setData(null);
            adapter.notifyDataSetChanged();
        }
    }

    private void listItemUpdated() {
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();

        if (calls != null) {
            calls.getRetailerMenuList(getActivity(), 1);
        }
        Toast.makeText(getActivity(), "Item updated !!!", Toast.LENGTH_SHORT).show();
    }

    public void showUpdatePopup(final DishesDetails dishesDetails) {
        if (dishesDetails != null) {
            // ...Irrelevant code for customizing the buttons and title
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setCancelable(false);
            dialog.setContentView(R.layout.retailer_alert_popup);

            TextView storeName = dialog.findViewById(R.id.popup_retailer_store_name);
            final TextView itemName = dialog.findViewById(R.id.popup_retailer_item_name);
            final EditText itemPrice = dialog.findViewById(R.id.popup_retailer_item_price);
            final EditText itemCount = dialog.findViewById(R.id.et_dishes_count);

            TextView btUpdate = dialog.findViewById(R.id.popup_retailer_buy);
            TextView btCancel = dialog.findViewById(R.id.popup_retailer_cancel);
            storeName.setText("Store Id " + dishesDetails.getRetailId());
            itemName.setText(dishesDetails.getItemName());
            itemPrice.setText("" + dishesDetails.getItemPrice());
            itemCount.setText("" + dishesDetails.getItemStock());

            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dishesDetails.setItemName(itemName.getText().toString());
                    String price = itemPrice.getText().toString().trim();
                    String count = itemCount.getText().toString().trim();
                    if (!price.isEmpty() && !count.isEmpty()) {

                        dishesDetails.setItemPrice(Integer.parseInt(price));
                        dishesDetails.setItemStock(Integer.parseInt(count));
                        if (isNetworkAvailable()) {

                            RetrofitNetworksCalls calls = DataSingleton
                                    .getInstance().getRetrofitNetworksCallsObject();
                            if (calls != null) {
                                calls.updateRetailerMenuItem(getActivity(), dishesDetails);
                                progressDialog.setMessage("Please wait..");
                                progressDialog.show();
                            }
                            dialog.dismiss();
                        } else {
                            if (mFragmentListener != null) {
                                mFragmentListener.showSnackBar();
                            }
                        }
                    }
                }
            });

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_retailer_item_delete:
                deleteItemFromList();
                break;
            case R.id.tv_list_back:
                if (mFragmentListener != null) {
                    mFragmentListener.onBackPress();
                }
                break;
            default:
                break;
        }
    }

    private void deleteItemFromList() {
        if (isNetworkAvailable()) {

            if (adapter != null) {

                List<DishesDetails> deletedItemsList = adapter.getDeletedItemsList();
                if (deletedItemsList != null && !deletedItemsList.isEmpty()) {
                    ArrayList<Integer> list = new ArrayList<>();
                    for (DishesDetails details : deletedItemsList) {
                        list.add(details.getMenuId());
                    }
                    RetrofitNetworksCalls calls = DataSingleton
                            .getInstance().getRetrofitNetworksCallsObject();
                    if (calls != null) {
                        progressDialog.setMessage("Please wait..");
                        progressDialog.show();
                        calls.deleteItemFromList(getActivity(), list);
                    }
                }
            }
        } else {
            if (mFragmentListener != null) {
                mFragmentListener.showSnackBar();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager ConnectionManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            return true;
        } else {
            return false;
        }

    }
}
