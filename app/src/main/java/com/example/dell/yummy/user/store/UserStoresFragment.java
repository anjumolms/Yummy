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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


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
        View view = inflater.inflate(R.layout.fragment_user_stores, container,
                false);
        recyclerView = view.findViewById(R.id.rv_stores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (StoreList != null) {

            UserStoresAdapter adapter = new UserStoresAdapter(getActivity(),
                    StoreList, miFragmentListener);

            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }
//        List<StoreDetails> dummyStoreList = new ArrayList<>();
//        StoreDetails storeDetails1 = new StoreDetails();
//        StoreDetails storeDetails2 = new StoreDetails();
//        StoreDetails storeDetails3 = new StoreDetails();
//        StoreDetails storeDetails4 = new StoreDetails();
//        StoreDetails storeDetails5 = new StoreDetails();
//        StoreDetails storeDetails6 = new StoreDetails();
//        StoreDetails storeDetails7 = new StoreDetails();
//        StoreDetails storeDetails8 = new StoreDetails();
//
//        storeDetails1.setRetailName("thediningroom");
//        storeDetails1.setRetailId(001);
//
//        storeDetails2.setRetailName("Family Cook OFF");
//        storeDetails2.setRetailId(002);
//
//        storeDetails3.setRetailName("ChickfilA");
//        storeDetails3.setRetailId(003);
//
//        storeDetails4.setRetailName("Kantari Chilly");
//        storeDetails4.setRetailId(004);
//
//        storeDetails5.setRetailName("gondola");
//        storeDetails5.setRetailId(005);
//
//        storeDetails6.setRetailName("plated");
//        storeDetails6.setRetailId(006);
//
//        storeDetails7.setRetailName("MXcocina");
//        storeDetails7.setRetailId(007);
//
//        storeDetails8.setRetailName("La Pandilla");
//        storeDetails8.setRetailId(001);
//
//        dummyStoreList.add(storeDetails1);
//        dummyStoreList.add(storeDetails2);
//        dummyStoreList.add(storeDetails3);
//        dummyStoreList.add(storeDetails4);
//        dummyStoreList.add(storeDetails5);
//        dummyStoreList.add(storeDetails6);
//        dummyStoreList.add(storeDetails7);
//        dummyStoreList.add(storeDetails8);
//        UserStoresAdapter adapter = new UserStoresAdapter(getActivity(),
//                dummyStoreList, miFragmentListener);
////
////            setting adapter to recyclerview
//       recyclerView.setAdapter(adapter);

        return view;
    }

    public void addListener(IFragmentListener miFragmentListener) {
        this.miFragmentListener = miFragmentListener;
    }

    public void setStoreDetails(List<StoreDetails> mStoreDetails) {
        this.StoreList = mStoreDetails;
    }
}
