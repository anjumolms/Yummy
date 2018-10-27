package com.example.dell.yummy.user.store;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.FontRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.model.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.model.StoreDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment implements View.OnClickListener {

    private List<DishesDetails> dishesList;
    private RecyclerView mRecyclerView;
    private IUserFragmentListener miUserFragmentListener;
    private TextView mStoreName;
    private StoreDetails mStoreDetails;
    private Button mProceed;
    private StoreDetailsAdapter adapter;
    private ProgressDialog mProgressDialog;
    private StoreDetails selectedStore;
    private TextView storeName;
    public StoreDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStoreDetails = (StoreDetails) bundle.getSerializable("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_details,
                container, false);
        initViews(view);
        mProceed.setOnClickListener(this);
        dishesList = new ArrayList<>();
        if (isNetworkAvailable()) {
            getStoreDishes();

        } else {
            if (miUserFragmentListener != null) {
                miUserFragmentListener.showSnackBar();
            }
        }
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_storedetails);
        storeName = view.findViewById(R.id.store_details_store_name);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mStoreName = view.findViewById(R.id.tv_store_name);
        mProceed = view.findViewById(R.id.bt_buy);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.toolbar_layout);

        if (selectedStore != null) {
            storeName.setText(selectedStore.getRetailName());
            collapsingToolbarLayout.setTitle("Yummy Aid");
        }
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void getStoreDishes() {
        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            if (selectedStore != null) {
                Call<List<DishesDetails>> call = iApiInterface
                        .getStoreMenu(selectedStore.getRetailId());
                call.enqueue(new Callback<List<DishesDetails>>() {
                    @Override
                    public void onResponse(Call<List<DishesDetails>> call,
                                           Response<List<DishesDetails>> response) {
                        if (response != null && response.code() == 200) {

                            dishesList = response.body();

                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), response.code()
                                    + response.message(), Toast.LENGTH_SHORT).show();
                        }
                        if (dishesList != null) {
                            mProgressDialog.dismiss();
                            adapter = new StoreDetailsAdapter(getActivity(), dishesList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DishesDetails>> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity(),
                                "invalid", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
    }

    public void addListener(IUserFragmentListener mIUserFragmentListener) {
        miUserFragmentListener = mIUserFragmentListener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_buy:
                if (miUserFragmentListener != null && adapter != null) {

                    List<DishesDetails> dishesDetails = adapter.getDishesDetailsList();
                    List<DishesDetails> selectedItems = new ArrayList<>();
                    for (DishesDetails details : dishesDetails) {
                        if (details.getCounter() >= 1) {
                            selectedItems.add(details);
                        }
                    }
                    if (selectedItems.size() > 0) {
                        int flag = 0;
                        for (DishesDetails details : selectedItems) {
                            if (details.getItemStock() >= details.getCounter()) {
                                flag = 0;
                            } else {
                                flag = 1;
                                Toast.makeText(getActivity(), "" + details.getItemName()
                                                + "Exceeded stock limit",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (flag == 0) {

                            miUserFragmentListener.loadConformationFragment(selectedItems);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Select Item to Proceed", Toast.LENGTH_LONG).show();
                        }
                }
                break;
            default:
                break;
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

    public void selectedStore(StoreDetails storeDetails) {
        selectedStore = storeDetails;
    }
}
