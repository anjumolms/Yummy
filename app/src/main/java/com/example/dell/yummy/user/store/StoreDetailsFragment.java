package com.example.dell.yummy.user.store;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.dell.yummy.IFragmentListener;
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
    private IFragmentListener miFragmentListener;
    private TextView mStoreName;
    private StoreDetails mStoreDetails;
    private Button mProceed;
    private StoreDetailsAdapter adapter;
    private ProgressDialog mProgressDialog;

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
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);
        initViews(view);
        mProceed.setOnClickListener(this);
        dishesList = new ArrayList<>();
        getStoreDishes();
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_storedetails);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoreName = view.findViewById(R.id.tv_store_name);
        mProceed = view.findViewById(R.id.bt_buy);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        if (mStoreDetails != null) {
            mStoreName.setText(mStoreDetails.getRetailName());
        }
    }

    private void getStoreDishes() {
        Retrofit retrofit = DataSingleton.getInstance().getRetrofitInstance();
        if (retrofit != null) {
            IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
            // TODO Hardcoded menu Id.
            Call<List<DishesDetails>> call = iApiInterface
                    .getStoreMenu(2);
            call.enqueue(new Callback<List<DishesDetails>>() {
                @Override
                public void onResponse(Call<List<DishesDetails>> call,
                                       Response<List<DishesDetails>> response) {
                    if (response != null && response.code() == 200) {

                        dishesList = response.body();

                    } else {
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
                    Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void addListener(IFragmentListener mIFragmentListener) {
        miFragmentListener = mIFragmentListener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_buy:
                if (miFragmentListener != null && adapter != null) {

                    List<DishesDetails> dishesDetails = adapter.getDishesDetailsList();
                    List<DishesDetails> selectedItems = new ArrayList<>();
                    for (DishesDetails details : dishesDetails) {
                        if (details.getCounter() >= 1) {
                            selectedItems.add(details);
                        }
                    }
                    if (selectedItems.size() > 0) {
                        miFragmentListener.loadConformationFragment(dishesDetails);
                    }
                }
                break;
            default:
                break;
        }
    }
}
