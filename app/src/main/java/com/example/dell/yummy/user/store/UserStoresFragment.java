package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStoresFragment extends Fragment {
    RecyclerView recyclerView;
    IFragmentListener miFragmentListener;
    List<StoreDetails> StoreList;


    public UserStoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_stores, container, false);
        recyclerView = view.findViewById(R.id.rv_stores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (StoreList != null) {

            UserStoresAdapter adapter = new UserStoresAdapter(getActivity(), StoreList, miFragmentListener);

            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    public void addListener(IFragmentListener miFragmentListener) {
        this.miFragmentListener = miFragmentListener;
    }

    public void setStoreDetails(List<StoreDetails> mStoreDetails) {
        this.StoreList = mStoreDetails;
    }
}
