package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.provider.SyncStateContract;
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

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment {

    List<DishesDetails> dishesList;
    RecyclerView recyclerView;
    IFragmentListener miFragmentListener;
    TextView storeName;
    StoreDetails storeDetails;
    Button proceed;
    StoreDetailsAdapter adapter;

    public StoreDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            storeDetails = (StoreDetails) bundle.getSerializable("key");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);
        recyclerView = view.findViewById(R.id.rv_storedetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storeName = view.findViewById(R.id.tv_store_name);
        proceed = view.findViewById(R.id.bt_buy);

        if (storeDetails != null) {
            storeName.setText(storeDetails.getRetailName());
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (miFragmentListener != null && adapter != null) {

                    List<DishesDetails> dishesDetails = adapter.getDishesDetailsList();

                    miFragmentListener.loadConformationFragment(dishesDetails);
                }

            }
        });



        dishesList = new ArrayList<>();
        addListItems();
        return view;
    }

    private void addListItems() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
        Call<List<DishesDetails>> call = iApiInterface
                .getStoreMenu(2);
        call.enqueue(new Callback<List<DishesDetails>>() {
            @Override
            public void onResponse(Call<List<DishesDetails>> call, Response<List<DishesDetails>> response) {
                if (response != null) {
                    if (response.code() == 200) {
                        dishesList = response.body();
                    } else {
                        Toast.makeText(getActivity(), response.code()
                                + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                if (dishesList != null) {

                    // UserDishesAdapter adapter = new UserDishesAdapter(getActivity(), dishesList,miUserViewListener);
                     adapter = new StoreDetailsAdapter(getActivity(), dishesList);

                    //setting adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<DishesDetails>> call, Throwable t) {
                Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addListener(IFragmentListener mIFragmentListener) {
        miFragmentListener = mIFragmentListener;

    }
}
